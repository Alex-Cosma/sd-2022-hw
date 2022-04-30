package com.assignment2.bookstoreapp.security;
import com.assignment2.bookstoreapp.security.dto.JwtResponse;
import com.assignment2.bookstoreapp.security.dto.LoginRequest;
import com.assignment2.bookstoreapp.security.dto.MessageResponse;
import com.assignment2.bookstoreapp.security.dto.SignupRequest;
import com.assignment2.bookstoreapp.user.dto.UserDetailsImpl;
import com.assignment2.bookstoreapp.user.model.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.assignment2.bookstoreapp.URLMapping.*;
import static com.assignment2.bookstoreapp.user.model.ERole.EMPLOYEE;

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
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JwtResponse jwtt = JwtResponse.builder()
                .token(jwt)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .roles(roles)
                .build();

        return ResponseEntity.ok(
                jwtt
        );
    }

    @PostMapping(SIGN_UP)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        Set<String> employeeRoles = new HashSet<>();
        employeeRoles.add(EMPLOYEE.name());
        signUpRequest.setRoles(employeeRoles);

        if (authService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        authService.register(signUpRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping(GET_ROLE)
    public String getRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        System.out.println(roles.get(0));

        return roles.get(0);
    }

}