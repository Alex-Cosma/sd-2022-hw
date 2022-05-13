package com.example.bookstore.security.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.util.Set;

@Data
@Builder
@Setter
public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private Set<String> roles;
}