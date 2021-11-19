//package com.example.test8.config;
//
//import io.r2dbc.spi.ConnectionFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
//
//@Configuration
//public class InitializeConfig {
//
//    @Bean
//    public ConnectionFactoryInitializer initializer(
//            @Qualifier("connectionFactory") ConnectionFactory connectionFactory
//    ) {
//        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
//        initializer.setConnectionFactory(connectionFactory);
//        return initializer;
//    }
//}
