package com.example.test8.service;

import com.example.test8.entity.UserMessage;
import com.example.test8.repo.UserMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class UserMessageService {

    private final UserMessageRepository userMessageRepository;

    @Autowired
    public UserMessageService(UserMessageRepository userMessageRepository) {
        this.userMessageRepository = userMessageRepository;
    }

    public Flux<UserMessage> findByText(String text){
        return userMessageRepository.findByText(text);
    };
}
