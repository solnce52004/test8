package com.example.test8.config;

import com.example.test8.entity.UserMessage;
import com.example.test8.handler.UserMessageHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

@Configuration
//@EnableWebFlux
public class RouterConfig {
    @Value("${server.address}")
    private String host;
    @Value("${server.port}")
    private String port;

//    private final UserMessageService userMessageService;

//    @Autowired
//    public RouterConfig(UserMessageService userMessageService) {
//        this.userMessageService = userMessageService;
//    }

    @Bean
    public WebClient webClient() {
        return WebClient
                .builder()
                .baseUrl(String.format("http://%s:%s", host, port))
                .build();
    }

    public Flux<String> getUserMessageText(){
        return webClient()
                .get()
                .uri("/text")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(UserMessage.class)
                .map(UserMessage::getText);
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

    @Bean
    public RouterFunction<ServerResponse> routeText() {
        return RouterFunctions
                .route()
                .GET(
                        "/text",
                        RequestPredicates.accept(MediaType.APPLICATION_JSON),
                        t -> ServerResponse
                                .ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.empty())
//                                .body(getUserMessageText(), String.class)
                )
                .build();
    }
}
