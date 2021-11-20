package com.example.test8.controller;

import com.example.test8.entity.UserMessage;
import com.example.test8.service.UserMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
@Log4j2

public class UserMessageController {

    private final UserMessageService userMessageService;

    @GetMapping("/text")
    public Flux<UserMessage> text(){
        final Flux<UserMessage> text = userMessageService.findByText("ttt");

//        text
//                .map(UserMessage::getText)
//                .subscribe(log::info);

        return text;
    }
}
