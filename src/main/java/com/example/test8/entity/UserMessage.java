package com.example.test8.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

//!!!!! это решает проблему определения репозитория!!!!
@org.springframework.data.relational.core.mapping.Table("user_messages")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    private String text;

    public UserMessage(String text) {
        this.text = text;
    }
}
