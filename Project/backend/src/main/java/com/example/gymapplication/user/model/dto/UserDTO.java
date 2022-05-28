package com.example.gymapplication.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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

    private List<String> trainings;

    private List<String> regularTrainings;
}
