package com.rdaniel.sd.project.security;

import com.rdaniel.sd.project.common.email.EmailSenderService;
import com.rdaniel.sd.project.customer.CustomerRepository;
import com.rdaniel.sd.project.security.dto.JwtResponse;
import com.rdaniel.sd.project.security.dto.LoginRequest;
import com.rdaniel.sd.project.security.dto.SignupRequest;
import com.rdaniel.sd.project.user.RoleRepository;
import com.rdaniel.sd.project.user.UserRepository;
import com.rdaniel.sd.project.user.model.Role;
import com.rdaniel.sd.project.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.rdaniel.sd.project.TestCreationFactory.newUser;
import static com.rdaniel.sd.project.user.model.RoleType.ROLE_CUSTOMER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceIntegrationTest {

  @Autowired
  private AuthService authService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private EmailSenderService emailSenderService;

  @Test
  void existsByUsername() {
    final User user = newUser();
    userRepository.save(user);

    final boolean exists = authService.existsByUsername(user.getUsername());
    assertTrue(exists);
  }

  @Test
  void existsByEmail() {
    final User user = newUser();
    userRepository.save(user);

    final boolean exists = authService.existsByEmail(user.getEmail());
    assertTrue(exists);
  }

  @Test
  void login() {
    final User user = newUser();
    final String userPassword = user.getPassword();
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);

    final LoginRequest loginRequest = LoginRequest.builder()
        .username(user.getUsername())
        .password(userPassword)
        .build();

    final JwtResponse jwtResponse = authService.login(loginRequest);
    assertNotNull(jwtResponse);
    assertEquals(user.getUsername(), jwtResponse.getUsername());
    assertEquals(user.getEmail(), jwtResponse.getEmail());
    assertEquals(user.getUsername(), jwtUtils.getUsernameFromJwtToken(jwtResponse.getToken()));
  }

  @Test
  void register() {
    final Role employeeRole = Role.builder().id(1L).name(ROLE_CUSTOMER).build();
    roleRepository.save(employeeRole);

    final SignupRequest signupRequest = SignupRequest.builder()
        .username("username")
        .email("username@email.com")
        .password("password")
        .build();

    authService.register(signupRequest);
  }
}