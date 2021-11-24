package com.example.test8.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

import java.util.HashMap;

@Configuration
@EnableWebFlux
@EnableSwagger2WebFlux
@Slf4j

public class WebConfig implements WebFluxConfigurer {

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resources/**")
//                .addResourceLocations("/public", "classpath:/static/");
//    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(
                        new ApiInfoBuilder()
                                .description("example api")
                                .title("example api")
                                .version("1.0.0")
                                .build()
                )
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();

    }

    @Bean
    public WebClient baseWebClient() {
        return WebClient
                .builder()
                .baseUrl("http://localhost:8080")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public Token getToken() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("email", "usernew@u.com");
        map.put("password", "123");
        final Token tokenS = new Token();

        baseWebClient()
                .post()
                .uri("/api/v1/auth/login")
                .body(BodyInserters.fromValue(map))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Token.class)
                .subscribe(v -> tokenS.setToken(v.getToken()));

        return tokenS;
    }
}