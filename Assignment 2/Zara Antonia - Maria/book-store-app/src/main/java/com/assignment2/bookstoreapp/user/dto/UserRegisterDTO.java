package com.assignment2.bookstoreapp.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder=true)
@AllArgsConstructor
public class UserRegisterDTO extends  UserMinimalDTO{
    private Set<String> roles;
    private String password;
}
