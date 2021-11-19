//package com.example.test8.config;
//
//import org.hibernate.SessionFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.orm.hibernate5.HibernateTemplate;
//import org.springframework.orm.hibernate5.HibernateTransactionManager;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
//@Configuration
//@EnableTransactionManagement
//@ComponentScan(basePackages = "com")
//@PropertySource(value = {"classpath:application.yaml"})
//
//public class DatabaseConfig {
//
//    @Bean(name = "entityManagerFactory") //обязательно!
//    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource);
//        sessionFactory.setPackagesToScan("com.*");
//        sessionFactory.setAnnotatedPackages("com.*");
//        sessionFactory.setHibernateProperties(hibernateProperties());
//
//        return sessionFactory;
//    }
//
//    @Bean
//    public Properties hibernateProperties() {
//        final Properties properties = new Properties();
//
//        try (InputStream in = DatabaseConfig.class
//                .getClassLoader()
//                .getResourceAsStream("hibernate.properties")) {
//
//            if (in != null) {
//                properties.load(in);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return properties;
//    }
//
//    @Bean
//    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
//        return new HibernateTransactionManager(sessionFactory);
//    }
//
//    @Bean
//    public HibernateTemplate hibernateTemplate(SessionFactory sessionFactory) {
//        return new HibernateTemplate(sessionFactory);
//    }
//}
