package com.example.test8.controller;

import com.example.test8.entity.UserMessage;
import com.example.test8.repo.UserMessageRepository;
import com.example.test8.service.UserMessageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@WebFluxTest
class UserMessageControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    UserMessageRepository userMessageRepository;
    @MockBean
    UserMessageService userMessageService;

    Long msgId = 123L;
    String text = "test";
    UserMessage msg = new UserMessage(msgId, text);

    @Test
    void findAll() {
        Mockito.when(userMessageService.findAll())
                .thenReturn(Flux.just(msg));

        webTestClient
                .get()
                .uri("/message/")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
//                .jsonPath("$.[0].id").isEqualTo(msgId)
                .jsonPath("$.[0].text").isEqualTo(text)
        ;
    }

    @Test
    void findById() {
        Mockito.when(userMessageService.findById(msgId))
                .thenReturn(Mono.just(msg));

        webTestClient
                .get()
                .uri("/message/" + msgId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void create() {
        Mockito.when(userMessageService.save(msg))
                .thenReturn(Mono.just(msg));

        webTestClient
                .post()
                .uri("/message")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(msg), UserMessage.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void put() {
        Mockito.when(userMessageService.findById(msgId))
                .thenReturn(Mono.just(msg));

        Mockito.when(userMessageService.update(msgId, msg))
                .thenReturn(Mono.just(msg));

        webTestClient
                .put()
                .uri("/message/" + msgId)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(msg), UserMessage.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void delete() {
        Mockito.when(userMessageService.findById(msgId))
                .thenReturn(Mono.just(msg));

        Mockito.when(userMessageService.findAndDelete(msgId))
                .thenReturn(Mono.empty());

        webTestClient
                .delete()
                .uri("/message/" + msgId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }
}