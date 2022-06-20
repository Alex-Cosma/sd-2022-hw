package com.rdaniel.sd.project.security;

import com.rdaniel.sd.project.BaseControllerTest;
import com.rdaniel.sd.project.security.dto.JwtResponse;
import com.rdaniel.sd.project.security.dto.LoginRequest;
import com.rdaniel.sd.project.security.dto.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.rdaniel.sd.project.UrlMappings.*;
import static com.rdaniel.sd.project.user.model.RoleType.ROLE_EMPLOYEE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest extends BaseControllerTest {

  @InjectMocks
  private AuthController authController;

  @Mock
  private AuthService authService;

  @BeforeEach
  protected void setUp() {
    super.setUp();
    authController = new AuthController(authService);
    mockMvc = MockMvcBuilders.standaloneSetup(authController)
        .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
        .build();
  }

  @Test
  void authenticateUser() throws Exception {
    final JwtResponse jwtResponse = JwtResponse.builder()
        .id(1L)
        .username("username")
        .roles(List.of(ROLE_EMPLOYEE.toString()))
        .email("email@email.com")
        .token("token")
        .build();

    when(authService.login(any(LoginRequest.class))).thenReturn(jwtResponse);

    final LoginRequest loginRequest = LoginRequest.builder()
        .username("username")
        .password("password")
        .build();


    final ResultActions response = performPostWithRequestBody(AUTH_PATH + SIGN_IN_PATH, loginRequest);

    response.andExpect(status().isOk())
        .andExpect(jsonContentToBe(jwtResponse))
        .andExpect(header().exists(HttpHeaders.AUTHORIZATION));
  }

  @Test
  void registerUser() throws Exception {
    final SignupRequest signupRequest = SignupRequest.builder()
        .username("username")
        .password("password")
        .email("email@email.com")
        .build();

    when(authService.existsByUsername(any(String.class))).thenReturn(false);
    when(authService.existsByEmail(any(String.class))).thenReturn(false);

    final ResultActions response = performPostWithRequestBody(AUTH_PATH + SIGN_UP_PATH, signupRequest);
    response.andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("User registered successfully!"));
  }
}