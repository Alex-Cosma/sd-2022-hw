package com.example.backend.dto.user;

public class SignInDto {

    public String username;
    public String password;

    public SignInDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public SignInDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
