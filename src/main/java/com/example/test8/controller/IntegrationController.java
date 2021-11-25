package com.example.test8.controller;

import com.example.test8.config.Token;
import com.example.test8.entity.UserExternal;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@RestController
@RequestMapping("/integration")
@Api
@Slf4j
@AllArgsConstructor

public class IntegrationController {
    private final WebClient baseWebClient;

    private WebClient webClient() {
        return WebClient
                .builder()
                .baseUrl("http://localhost:8080")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(logRequest())
                .filter(logResponseStatus())
                .build();
    }

    private HashMap<String, String> getCredentials() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("email", "usernew@u.com");
        map.put("password", "123");
        return map;
    }

    private Flux<UserExternal> getUserExternalFlux(String uri) {
        return baseWebClient
                .post()
                .uri("/api/v1/auth/login")
                .body(BodyInserters.fromValue(getCredentials()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Token.class)
                .map(Token::getToken)
                .flatMapMany(
                        token -> webClient()
                                .get()
                                .uri(uri)
                                .header("Authorization", "Bearer " + token)
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToFlux(UserExternal.class)
                );
    }

    @GetMapping("/users")
    public Flux<UserExternal> getUsers() {
        return getUserExternalFlux("/api/v1/users/");
    }

    @GetMapping("/users/{id}")
    public Flux<UserExternal> getUserById(
            @PathVariable("id") Long id
    ) {
        return getUserExternalFlux("/api/v1/users/" + id);
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