package com.app.banking.security.controller;

import com.app.banking.security.details.UserDetailsImpl;
import com.app.banking.security.dto.JwtResponse;
import com.app.banking.security.dto.LoginRequest;
import com.app.banking.security.dto.MessageResponse;
import com.app.banking.security.dto.SignupRequest;
import com.app.banking.security.jwt.JwtUtils;
import com.app.banking.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static com.app.banking.URLMapping.*;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @PostMapping(SIGN_IN)
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                JwtResponse.builder()
                        .token(jwt)
                        .id(userDetails.getId())
                        .username(userDetails.getUsername())
                        .email(userDetails.getEmail())
                        .roles(roles)
                        .build()
        );
    }

    @PostMapping(SIGN_UP)
    public ResponseEntity<?> registerUser(@RequestBody @Validated SignupRequest signUpRequest) {
        if (authService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (authService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        authService.register(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
