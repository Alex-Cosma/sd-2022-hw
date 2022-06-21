package com.example.demo.user.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Set<String> roles;
}