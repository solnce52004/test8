package com.example.test8.config;

import com.example.test8.repo.UserMessageRepository;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.mapping.R2dbcMappingContext;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
//@EnableTransactionManagement
//@ComponentScan(basePackages = "com")
@EnableR2dbcRepositories(basePackages = "com.example.test8.repo")
@PropertySource(value = {"classpath:application.yaml"})

public class DatabaseConfig
        extends AbstractR2dbcConfiguration {
    @Value("${server.address}")
    private String host;

    @Value("${server.port}")
    private int port;

    @Value("${spring.r2dbc.username}")
    private String username;

    @Value("${spring.r2dbc.password}")
    private String password;

    @Value("${spring.r2dbc.properties.database}")
    private String database;

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(
                ConnectionFactoryOptions
                        .builder()
                        .option(ConnectionFactoryOptions.DRIVER, "mysql")
                        .option(ConnectionFactoryOptions.HOST, host)
                        .option(ConnectionFactoryOptions.PORT, port)
                        .option(ConnectionFactoryOptions.USER, username)
                        .option(ConnectionFactoryOptions.PASSWORD, password)
                        .option(ConnectionFactoryOptions.DATABASE, database)
                        .build());
    }

    @Bean
    public DatabaseClient r2dbcDatabaseClient() {
        return DatabaseClient
                .builder()
                .connectionFactory(connectionFactory())
                .build();
    }

    @Bean
    public R2dbcRepositoryFactory r2dbcRepositoryFactory() {
        R2dbcMappingContext context = new R2dbcMappingContext();
        context.afterPropertiesSet();

        return new R2dbcRepositoryFactory(
                r2dbcDatabaseClient(),
                reactiveDataAccessStrategy(
                        r2dbcConverter(context, r2dbcCustomConversions())
                )
        );
    }

    @Bean
    public UserMessageRepository userMessageRepository(R2dbcRepositoryFactory r2dbcRepositoryFactory) {
        return r2dbcRepositoryFactory.getRepository(UserMessageRepository.class);
    }
}
