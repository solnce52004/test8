package com.example.test8.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "user_messages")
@Data
@AllArgsConstructor

public class UserMessage {
    @Id
    private Long id;
    private String text;

    public UserMessage(String text) {
        this.text = text;
    }
}
