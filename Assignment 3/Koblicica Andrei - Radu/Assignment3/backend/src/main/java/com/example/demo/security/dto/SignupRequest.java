package com.example.demo.security.dto;

import com.example.demo.movie.model.Movie;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Data
@Builder
@Setter
public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private Set<String> roles;
    private List<Movie> watchlist;
}