package com.example.test8.controller;

import com.example.test8.dto.UserLoginDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Controller
@Slf4j

public class HomeController {

    @GetMapping(path = {"/","/index"})
    public Mono<ServerResponse> hello() {
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_HTML)
                .render("index");
    }

    @GetMapping(path = {"/login"})
    public String login() {
        return "login";
    }

    @PostMapping(path = {"/login"})
    public String loginPost(
         @RequestBody UserLoginDTO userDTO
    ) {
        log.info(userDTO.toString());
        return "login";
    }
}
