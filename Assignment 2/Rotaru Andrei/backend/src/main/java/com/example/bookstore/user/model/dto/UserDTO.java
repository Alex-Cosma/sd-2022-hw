package com.example.bookstore.user.model.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @Email(message = "Email is not valid")
    @NotNull()
    private String email;

    @Size(min = 6 , message = "Password must contain at least 6 characters")
    @NotNull()
    private String password;

    @Size(min = 4 , message = "Username must contain at least 4 characters")
    @NotNull()
    private String username;

    private String role;
}
