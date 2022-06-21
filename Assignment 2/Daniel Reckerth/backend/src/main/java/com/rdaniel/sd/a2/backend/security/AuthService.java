package com.rdaniel.sd.a2.backend.security;

import com.rdaniel.sd.a2.backend.security.dto.JwtResponse;
import com.rdaniel.sd.a2.backend.security.dto.LoginRequest;
import com.rdaniel.sd.a2.backend.security.dto.SignupRequest;
import com.rdaniel.sd.a2.backend.user.RoleRepository;
import com.rdaniel.sd.a2.backend.user.UserRepository;
import com.rdaniel.sd.a2.backend.user.dto.UserDetailsImpl;
import com.rdaniel.sd.a2.backend.user.model.Role;
import com.rdaniel.sd.a2.backend.user.model.RoleType;
import com.rdaniel.sd.a2.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  public JwtResponse login(LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(),
            loginRequest.getPassword()
        )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    return JwtResponse.fromUserDetails(userDetails, jwt);
  }

  public void register(SignupRequest signupRequest) {
    User user = User.builder()
        .username(signupRequest.getUsername())
        .email(signupRequest.getEmail())
        .password(passwordEncoder.encode(signupRequest.getPassword()))
        .build();

    Set<String> roleStr = signupRequest.getRoles();
    Set<Role> roles = new HashSet<>();

    if (roleStr == null) {
      Role defaultRole = roleRepository.findByName(RoleType.EMPLOYEE)
          .orElseThrow(() -> new RuntimeException("Cannot find EMPLOYEE role"));
      roles.add(defaultRole);
    } else {
      roleStr.forEach(role -> {
        Role r = roleRepository.findByName(RoleType.valueOf(role))
            .orElseThrow(() -> new RuntimeException("Cannot find role: " + role));
        roles.add(r);
      });
    }

    user.setRoles(roles);
    userRepository.save(user);
  }
}
