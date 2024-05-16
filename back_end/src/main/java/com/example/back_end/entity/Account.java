package com.example.back_end.entity;

import lombok.Data;

@Data
public class Account {
    int id;
    String email;
    String username;
    String password;
}
