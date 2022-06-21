package com.app.banking.security.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JwtResponse {

    private Integer id;
    private String token;
    private String username;
    private String email;
    private List<String> roles;

}
