package com.project.clinic.security.dto;

import com.project.clinic.treatment.model.Treatment;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class SignupRequest {
    private String username;
    private String password;
    private Long skinColorId;
    private Set<String> roles;
    private Set<Treatment> treatments;

    public SignupRequest(String username, String password, Long skinColorId, Set<String> roles, Set<Treatment> treatments) {
        this.username = username;
        this.password = password;
        this.skinColorId = skinColorId;
        this.roles = roles;
        this.treatments = treatments;
    }
}