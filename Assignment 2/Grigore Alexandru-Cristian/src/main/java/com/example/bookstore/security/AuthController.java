package com.example.bookstore.security;

import com.example.bookstore.security.dto.JwtResponse;
import com.example.bookstore.security.dto.LoginRequest;
import com.example.bookstore.security.dto.MessageResponse;
import com.example.bookstore.security.dto.SignupRequest;
import com.example.bookstore.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


import static com.example.bookstore.UrlMappings.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @PostMapping(SIGN_IN)
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(
               JwtResponse.fromUser(userDetails, jwt)
        );
    }

    @PostMapping(SIGN_UP)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){

        if (authService.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (authService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        authService.register(signupRequest);


        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
