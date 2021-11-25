package com.example.test8.config.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> route(UserMessageHandler userMessageHandler) {
        return RouterFunctions
//                .route(
//                        RequestPredicates
//                                .GET("/index")
//                                .and(RequestPredicates.accept(MediaType.TEXT_HTML)),
//                        userMessageHandler::showIndex
//                )
                .route(
                        RequestPredicates
                                .GET("/text"),
                        userMessageHandler::getServerResponseFlux
                )
                .andRoute(
                        RequestPredicates
                                .GET("/text2"),
                        userMessageHandler::getServerResponseMono
                );
    }
}
