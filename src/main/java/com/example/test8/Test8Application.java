package com.example.test8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = "com.*")
//@ComponentScan(basePackages = "com.*")
@EnableR2dbcRepositories(basePackages = "com.*")
@PropertySource(value = {"classpath:application.yaml"})

public class Test8Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Test8Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Test8Application.class);
	}
}
