package com.example.test8.repo;

import com.example.test8.entity.UserMessage;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;

//@R2dbcRepository
public interface UserMessageRepository extends ReactiveSortingRepository<UserMessage, Long> {

    @Query("select * from user_messages where text = :text")
    Flux<UserMessage> findByText(String text);
}
