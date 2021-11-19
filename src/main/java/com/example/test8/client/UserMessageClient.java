package com.example.test8.client;

import com.example.test8.entity.UserMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class UserMessageClient {
   private final WebClient webClient;

   public Mono<String> getUserMessageText(){
       return webClient
               .get()
               .uri("/index")
               .accept(MediaType.APPLICATION_JSON)
               .retrieve()
               .bodyToMono(UserMessage.class)
               .map(UserMessage::getText);
   }
}
