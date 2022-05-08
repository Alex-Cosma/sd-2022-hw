package com.lab4.demo.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Set;


@Data
@SuperBuilder
@AllArgsConstructor
public class UserListDTO {
    private String email;
    private Long id;
    private String username;
    private String password;
    private Set<String> roles;
}
