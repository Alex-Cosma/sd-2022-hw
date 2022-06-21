package com.example.demo.user.dto;

import com.example.demo.movie.model.Movie;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
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