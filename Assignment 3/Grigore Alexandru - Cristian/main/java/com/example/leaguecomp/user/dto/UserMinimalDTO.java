package com.example.leaguecomp.user.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserMinimalDTO {
    private Long id;
    private String name;
}
