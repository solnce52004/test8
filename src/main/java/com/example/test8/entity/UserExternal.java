package com.example.test8.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserExternal {
    private Long id;
    private String username;
    private String status;
    private HashSet<Object> roles;
}
