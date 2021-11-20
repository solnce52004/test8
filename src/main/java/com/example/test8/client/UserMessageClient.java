//package com.example.test8.client;
//
//import com.example.test8.entity.UserMessage;
//import lombok.AllArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Flux;
//
//@Component
//@AllArgsConstructor
//public class UserMessageClient {
//   private final WebClient webClient;
//
//   public Flux<String> getUserMessageText(){
//       return webClient
//               .get()
//               .uri("/text")
//               .accept(MediaType.APPLICATION_JSON)
//               .retrieve()
//               .bodyToFlux(UserMessage.class)
//               .map(UserMessage::getText);
//   }
//}
