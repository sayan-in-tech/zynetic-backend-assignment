package com.sayan.bookstore.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
