package com.example.backend.dto.user;

import com.sun.istack.NotNull;

import javax.validation.constraints.Size;

public class UserCreationDto {

    @NotNull
    @Size(min = 3, max = 20)
    public String username;

    @NotNull
    @Size(min = 3, max = 20)
    public String password;

    public UserCreationDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserCreationDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
