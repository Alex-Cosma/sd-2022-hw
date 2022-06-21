package com.app.bookingapp.security.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JwtResponse {

    private String token;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

}
