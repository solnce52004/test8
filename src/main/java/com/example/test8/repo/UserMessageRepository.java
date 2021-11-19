package com.example.test8.repo;

import com.example.test8.entity.UserMessage;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserMessageRepository extends ReactiveCrudRepository<UserMessage, Long> {

    @Query("select * from user_messages where text = :text")
    Flux<UserMessage> findByText(String text);
}
