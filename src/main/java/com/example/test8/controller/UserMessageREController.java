package com.example.test8.controller;

import com.example.test8.entity.UserMessage;
import com.example.test8.service.UserMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@RestController
@RequestMapping("/re/message")
@AllArgsConstructor
@Slf4j
@Api(description = "Сообщения")

public class UserMessageREController {

    private final UserMessageService userMessageService;

    @ApiOperation(value = "Отображение всех сообщений пользователей")
    @GetMapping
    public Flux<UserMessage> findAll() {
        log.info("findAll");
        return userMessageService.findAll();
    }

    @GetMapping(value = "/{msgId}")
    public Mono<ResponseEntity<UserMessage>> findById(
            @PathVariable("msgId") Long msgId
    ) {
        return userMessageService
                .findById(msgId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(
                        ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .build()
                );
    }

    @PostMapping
    public Mono<UserMessage> create(
            @RequestBody UserMessage msg
    ) {
        return userMessageService.save(msg);
    }

    @PutMapping(value = "/{msgId}")
    public Mono<ResponseEntity<HashMap<String, Object>>> put(
            @PathVariable("msgId") Long msgId,
            @RequestBody UserMessage msg
    ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        final HashMap<String, Object> body = new HashMap<>();
        body.put("body", null);

        return userMessageService
                .findById(msgId)
                .flatMap(t -> {
                    msg.setId(t.getId());
                    return userMessageService.save(msg);
                })
                .map(t -> {
                            body.put("body", t);
                            return ResponseEntity
                                    .accepted()
                                    .headers(headers)
                                    .body(body);
                        }
                )
                .defaultIfEmpty(
                        new ResponseEntity<>(body, headers, HttpStatus.NOT_FOUND)
                );
    }

    @DeleteMapping(value = "/{msgId}")
    public Mono<ResponseEntity<Void>> delete(
            @PathVariable("msgId") Long msgId
    ) {
        return userMessageService
                .findById(msgId)
                .flatMap(
                        t -> userMessageService
                                .delete(t.getId())
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
