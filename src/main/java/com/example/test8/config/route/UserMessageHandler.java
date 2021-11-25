package com.example.test8.config.route;

import com.example.test8.entity.UserMessage;
import com.example.test8.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class UserMessageHandler {

    private final UserMessageService userMessageService;

    @Autowired
    public UserMessageHandler(UserMessageService userMessageService) {
        this.userMessageService = userMessageService;
    }

    public Mono<ServerResponse> showIndex(ServerRequest request) {
        return ServerResponse
                .ok()
                .render("index", new ModelMap());
    }

    public Mono<ServerResponse> showMessage(ServerRequest request) {
        return ServerResponse
                .ok()
//                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new UserMessage("это тестовое сообщение")));
    }

    public Mono<ServerResponse> getServerResponseMono(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .render(
                        "text",
                        Map.of(
                                "text",
                                userMessageService.findById(1L).map(UserMessage::getText)
                        )
                );
    }

    public Mono<ServerResponse> getServerResponseFlux(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        userMessageService.findByText("nnn"),
                        UserMessage.class

//                        BodyInserters.fromPublisher(
//                                userMessageService.findByText("nnn").map(UserMessage::getText),
//                                String.class
//                        )
                );
    }
}
