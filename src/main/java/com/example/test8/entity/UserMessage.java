package com.example.test8.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "user_messages")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserMessage {
    @Id
    private Long id;
    private String text;

    public UserMessage(String text) {
        this.text = text;
    }
}
