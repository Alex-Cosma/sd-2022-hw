package com.rdaniel.sd.a2.backend.security;

import com.rdaniel.sd.a2.backend.security.dto.JwtResponse;
import com.rdaniel.sd.a2.backend.security.dto.LoginRequest;
import com.rdaniel.sd.a2.backend.security.dto.MessageResponse;
import com.rdaniel.sd.a2.backend.security.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.rdaniel.sd.a2.backend.UrlMappings.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(AUTH_PATH)
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authenticationService;

  @PostMapping(SIGN_IN_PATH)
  public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    try {
      final JwtResponse jwtResponse = authenticationService.login(loginRequest);

      return ResponseEntity.ok()
          .header(HttpHeaders.AUTHORIZATION, jwtResponse.getToken())
          .body(jwtResponse);
    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  @PostMapping(SIGN_UP_PATH)
  public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest loginRequest) {
    if (authenticationService.existsByUsername(loginRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    if (authenticationService.existsByEmail(loginRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    authenticationService.register(loginRequest);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
