package com.rdaniel.sd.project.security;

import com.rdaniel.sd.project.common.email.EmailSenderService;
import com.rdaniel.sd.project.customer.CustomerRepository;
import com.rdaniel.sd.project.customer.model.Customer;
import com.rdaniel.sd.project.security.dto.JwtResponse;
import com.rdaniel.sd.project.security.dto.LoginRequest;
import com.rdaniel.sd.project.security.dto.SignupRequest;
import com.rdaniel.sd.project.user.RoleRepository;
import com.rdaniel.sd.project.user.UserRepository;
import com.rdaniel.sd.project.user.dto.UserDetailsImpl;
import com.rdaniel.sd.project.user.model.Role;
import com.rdaniel.sd.project.user.model.RoleType;
import com.rdaniel.sd.project.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final CustomerRepository customerRepository;
  private final RoleRepository roleRepository;

  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  private final EmailSenderService emailSenderService;

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

  @Transactional
  public void register(SignupRequest signupRequest) {
    User user = User.builder()
        .username(signupRequest.getUsername())
        .email(signupRequest.getEmail())
        .password(passwordEncoder.encode(signupRequest.getPassword()))
        .build();

    Set<String> roleStr = signupRequest.getRoles();
    Set<Role> roles = new HashSet<>();

    if (roleStr == null) {
      Role defaultRole = roleRepository.findByName(RoleType.ROLE_CUSTOMER)
          .orElseThrow(() -> new RuntimeException("Cannot find CUSTOMER role"));
      roles.add(defaultRole);
    } else {
      roleStr.forEach(role -> {
        Role r = roleRepository.findByName(RoleType.valueOf(role))
            .orElseThrow(() -> new RuntimeException("Cannot find role: " + role));
        roles.add(r);
      });
    }

    if(roles.contains(roleRepository.findByName(RoleType.ROLE_CUSTOMER).orElseThrow(() -> new RuntimeException("Cannot find CUSTOMER role")))) {
      Customer customer = Customer.builder()
          .username(signupRequest.getUsername())
          .email(signupRequest.getEmail())
          .password(passwordEncoder.encode(signupRequest.getPassword()))
          .roles(roles)
          .build();
      customerRepository.save(customer);
      emailSenderService.sendEmail(customer.getEmail(), "Account registered!", "Welcome to the Help Desk Support of your devices!\n " +
          "Your account has been successfully created and you have a customer account.");
    } else {
      user.setRoles(roles);
      userRepository.save(user);
      emailSenderService.sendEmail(user.getEmail(), "Account registered!", "Welcome to the Help Desk Support of your devices!\n " +
          "Your account has been successfully created!");
    }
  }
}
