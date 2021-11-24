package com.example.test8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = "com.*")
@ComponentScan(basePackages = "com.*")
@EnableR2dbcRepositories(basePackages = "com.*")
@PropertySource(value = {"classpath:application.yaml"})

public class Test8Application {

	public static void main(String[] args) {
		SpringApplication.run(Test8Application.class, args);
	}
}
