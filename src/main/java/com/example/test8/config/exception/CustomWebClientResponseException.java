package com.example.test8.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
@Getter
public class CustomWebClientResponseException extends Exception {
    private HttpStatus httpStatus = HttpStatus.FORBIDDEN;

    public CustomWebClientResponseException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    public CustomWebClientResponseException(String msg) {
        super(msg);
    }
}