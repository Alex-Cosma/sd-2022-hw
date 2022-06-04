package com.rdaniel.sd.a2.backend.security.dto;

import com.rdaniel.sd.a2.backend.user.dto.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {

  private Long id;

  private String username;

  private String email;

  private String token;

  private List<String> roles;

  public static JwtResponse fromUserDetails(UserDetailsImpl userDetails, String jwtToken) {
    List<String> roles = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(java.util.stream.Collectors.toList());

    return JwtResponse.builder()
        .id(userDetails.getId())
        .username(userDetails.getUsername())
        .email(userDetails.getEmail())
        .token(jwtToken)
        .roles(roles)
        .build();
  }
}
