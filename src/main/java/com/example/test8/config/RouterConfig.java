package com.example.test8.config;

import com.example.test8.handler.UserMessageHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {
    @Value("${server.address}")
    private String host;
    @Value("${server.port}")
    private String port;

    @Bean
    public WebClient webClient(){
        return WebClient
                .builder()
                .baseUrl(String.format("http://%s:%s", host, port))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> route(UserMessageHandler userMessageHandler) {
        return RouterFunctions
                .route(
                        RequestPredicates
                                .GET("/index")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        userMessageHandler::showMessage
                );
    }
}
