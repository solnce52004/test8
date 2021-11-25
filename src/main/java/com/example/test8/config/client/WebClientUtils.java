package com.example.test8.config.client;

import com.example.test8.config.exception.CustomWebClientResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import reactor.core.publisher.Mono;

@Component
@Slf4j

public class WebClientUtils {

    public ExchangeFilterFunction errorHandlingFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(
                clientResponse -> {
                    final HttpStatus statusCode = clientResponse.statusCode();

                    if (statusCode.is5xxServerError() || statusCode.is4xxClientError()) {
                        return clientResponse
                                .bodyToMono(String.class)
                                .flatMap(
                                        errorBody ->
                                                Mono.error(new CustomWebClientResponseException(errorBody, statusCode)
                                        ));
                    } else {
                        return Mono.just(clientResponse);
                    }
                });
    }

    public ExchangeFilterFunction errorExchangeFilterFunctionsFilter() {
        return ExchangeFilterFunctions.statusError(
                httpStatusCode -> httpStatusCode.is5xxServerError() || httpStatusCode.is4xxClientError(),
                (clientResponse) ->
                                new CustomWebClientResponseException(
                                        "CustomWebClientResponseException",
                                        clientResponse.statusCode()
                                ));
    }


    public ExchangeFilterFunction logRequest() {
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

    public ExchangeFilterFunction logResponseStatus() {
        return ExchangeFilterFunction.ofResponseProcessor(
                clientResponse -> {
                    log.info("Response Status {}", clientResponse.statusCode());
                    return Mono.just(clientResponse);
                }
        );
    }
}
