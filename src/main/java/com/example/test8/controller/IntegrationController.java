package com.example.test8.controller;

import com.example.test8.config.client.WebClientUtils;
import com.example.test8.dto.ExternalTokenDTO;
import com.example.test8.entity.UserExternal;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;

import java.util.HashMap;

@RestController
@RequestMapping("/integration")
@Api
@Slf4j
@AllArgsConstructor

public class IntegrationController {
    private final WebClientUtils webClientUtils;

    public WebClient baseWebClient() {
        return WebClient
                .builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(webClientUtils.errorHandlingFilter())
                .filter(webClientUtils.logRequest())
                .filter(webClientUtils.logResponseStatus())
                .baseUrl("http://localhost:8080")
                .build();
    }

    private HashMap<String, String> getCredentials() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("email", "usernew@u.com");
        map.put("password", "123");
        return map;
    }

    private Flux<UserExternal> getUserExternalFlux(String uri) {
        return baseWebClient()
                .post()
                .uri("/api/v1/auth/login")
                .body(BodyInserters.fromValue(getCredentials()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ExternalTokenDTO.class)
                .map(ExternalTokenDTO::getToken)
                .flatMapMany(
                        token -> baseWebClient()
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