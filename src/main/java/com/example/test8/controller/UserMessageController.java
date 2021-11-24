package com.example.test8.controller;

import com.example.test8.entity.UserMessage;
import com.example.test8.service.UserMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/message")
@AllArgsConstructor
@Slf4j
@Api(description = "Сообщения")

public class UserMessageController {

    private final UserMessageService userMessageService;

    @ApiOperation(value = "Отображение всех сообщений пользователей")
    @GetMapping
    public Flux<UserMessage> findAll() {
        log.info("findAll");
        return userMessageService.findAll();
    }

    @GetMapping(value = "/{msgId}")
    public Mono<UserMessage> findById(
            @PathVariable("msgId") Long msgId
    ) {
//        text.map(UserMessage::getText).subscribe(log::info);
        return userMessageService.findById(msgId);
    }

    @PostMapping
    public Mono<UserMessage> create(
            @RequestBody UserMessage msg
    ) {
        return userMessageService.save(msg);
    }

    @PutMapping(value = "/{msgId}")
    public Mono<UserMessage> put(
            @PathVariable("msgId") Long msgId,
            @RequestBody UserMessage msg
    ) {
        return userMessageService.update(msgId, msg);
    }

    @DeleteMapping (value = "/{msgId}")
    public Mono<Void> delete(
            @PathVariable("msgId") Long msgId
    ) {
        return userMessageService.findAndDelete(msgId);
    }
}
