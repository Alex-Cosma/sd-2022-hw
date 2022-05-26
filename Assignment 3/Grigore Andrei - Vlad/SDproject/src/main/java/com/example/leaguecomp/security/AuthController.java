package com.example.leaguecomp.security;

import com.example.leaguecomp.security.dto.JwtResponse;
import com.example.leaguecomp.security.dto.LoginRequest;
import com.example.leaguecomp.security.dto.MessageResponse;
import com.example.leaguecomp.security.dto.SignupRequest;

import com.example.leaguecomp.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.leaguecomp.UrlMappings.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @PostMapping(SIGN_IN)
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println("why are you here");
        System.out.println("YOU ARE HERE");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(
                JwtResponse.fromUser(userDetails,jwt)
        );
    }

    @PostMapping(SIGN_UP)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
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
