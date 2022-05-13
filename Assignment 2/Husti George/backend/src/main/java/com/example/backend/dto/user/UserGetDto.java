package com.example.backend.dto.user;

import com.sun.istack.NotNull;

import javax.validation.constraints.Size;

public class UserGetDto {

    @NotNull
    public int id;

    @NotNull
    @Size(min = 3, max = 20)
    public String username;

    public UserGetDto setId(int id) {
        this.id = id;
        return this;
    }

    public UserGetDto setUsername(String username) {
        this.username = username;
        return this;
    }
}
