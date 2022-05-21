package com.example.airbnb.user.dto;

import com.example.airbnb.accommodation.model.Accommodation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@SuperBuilder
@AllArgsConstructor
@Builder
public class UserListDTO extends UserMinimalDTO{
    private String email;
    private Set<String> roles;
    private String password;
    private Set<Accommodation> accommodations = new HashSet<>();

    public UserListDTO() {

    }
}