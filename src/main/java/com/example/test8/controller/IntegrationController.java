package com.example.test8.controller;

import com.example.test8.config.Token;
import com.example.test8.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/integration")
//@Api
@Slf4j
@AllArgsConstructor

public class IntegrationController {

    private final Token token;

    public String getToken() {
        return token.getToken() != null
                ? token.getToken()
                : "";
    }

    private WebClient webClient() {

        return WebClient
                .builder()
                .baseUrl("http://localhost:8080")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Authorization", "Bearer " + getToken())
                .filter(logRequest())
                .filter(logResponseStatus())
                .build();
    }

    @GetMapping("/users")
    public Flux<User> getUsers() {
        return webClient()
                .get()
                .uri("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, clientResponse ->
//                        Mono.error(new MyCustomClientException())
//                )
//                .onStatus(HttpStatus::is5xxServerError, clientResponse ->
//                        Mono.error(new MyCustomServerException())
//                )
                .bodyToFlux(User.class);
    }

    @GetMapping("/users/{id}")
    public Flux<User> getUserById(
            @PathVariable("id") Long id
    ) {
        return webClient()
                .get()
                .uri("/api/v1/users/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(User.class);
    }

    ///////////
    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(
                clientRequest -> {
                    log.info("Request: {} {}", clientRequest.method(), clientRequest.url());

                    clientRequest
                            .headers()
                            .forEach((name, values) -> values
                                    .forEach(value -> log.info("{}={}", name, value))
                            );
                    return Mono.just(clientRequest);
                }
        );
    }

    private ExchangeFilterFunction logResponseStatus() {
        return ExchangeFilterFunction.ofResponseProcessor(
                clientResponse -> {
                    log.info("Response Status {}", clientResponse.statusCode());
                    return Mono.just(clientResponse);
                }
        );
    }

    ///////////
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<String> handleWebClientResponseException(
            WebClientResponseException ex
    ) {
        log.error("Error from WebClient - Status {}, Body {}", ex.getRawStatusCode(), ex.getResponseBodyAsString(), ex);

        return ResponseEntity
                .status(ex.getRawStatusCode())
                .body(ex.getResponseBodyAsString());
    }
}