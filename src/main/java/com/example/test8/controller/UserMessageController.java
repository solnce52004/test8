package com.example.test8.controller;

import com.example.test8.client.UserMessageClient;
import com.example.test8.entity.UserMessage;
import com.example.test8.service.UserMessageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
public class UserMessageController {

    private final UserMessageClient userMessageClient;
    private final UserMessageService userMessageService;

    @GetMapping("/index")
    public Flux<UserMessage> index(){
        final String text = userMessageClient.getUserMessageText().block();
        System.out.println(text);

        return userMessageService.findByText("ttt");
    }
}
