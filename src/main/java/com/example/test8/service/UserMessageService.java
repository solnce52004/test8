package com.example.test8.service;

import com.example.test8.entity.UserMessage;
import com.example.test8.repo.UserMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserMessageService {

    private final UserMessageRepository userMessageRepository;

    @Autowired
    public UserMessageService(UserMessageRepository userMessageRepository) {
        this.userMessageRepository = userMessageRepository;
    }

    public Flux<UserMessage> findByText(String text) {
        return userMessageRepository.findByText(text);
    }

    public Mono<UserMessage> findById(Long id) {
        return userMessageRepository.findById(id);
    }

    public Flux<UserMessage> findAll() {
        return userMessageRepository.findAll();
    }

    public Mono<UserMessage> save(UserMessage msg) {
        return userMessageRepository.save(msg);
    }

    public Mono<UserMessage> update(Long id, UserMessage msg) {
        return findById(id)
                .flatMap(t -> {
                    msg.setId(id);
                    return userMessageRepository.save(msg);
                });
    }

    public Mono<Void> findAndDelete(Long id) {
        return findById(id)
                .flatMap(t -> userMessageRepository.deleteById(t.getId()));
    }

    public Mono<Void> delete(Long id) {
        return userMessageRepository.deleteById(id);
    }
}
