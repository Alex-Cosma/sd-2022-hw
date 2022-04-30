package com.assignment2.bookstoreapp.security.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class SignupRequest {
    private String username;
    private String password;
    private Set<String> roles;
}