package com.example.leaguecomp.user.dto;

import com.example.leaguecomp.summoner.model.Summoner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserListDTO extends UserMinimalDTO{
    private String email;
    private Set<String> roles;
    private Set<Summoner> summoners;
}
