package com.example.bookstore.user.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserListDTO extends UserMinimalDTO {
    private String email;
    private String password;
    private Set<String> roles;
}