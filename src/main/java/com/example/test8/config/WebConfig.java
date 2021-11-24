package com.example.test8.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@Configuration
@EnableWebFlux
@EnableSwagger2WebFlux
//@EnableR2dbcRepositories(basePackages = "com.*")

public class WebConfig implements WebFluxConfigurer {
//    @Value("${server.address}")
//    private String host;
//    @Value("${server.port}")
//    private String port;
//
//    @Bean
//    public WebClient webClient() {
//        return WebClient
//                .builder()
//                .baseUrl(String.format("http://%s:%s", host, port))
//                .build();
//    }
//
//    public Flux<String> getUserMessageText(){
//        return webClient()
//                .get()
//                .uri("/text")
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToFlux(UserMessage.class)
//                .map(UserMessage::getText);
//    }


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
}
