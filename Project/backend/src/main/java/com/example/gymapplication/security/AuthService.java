package com.example.gymapplication.security;

import com.example.gymapplication.email.EmailService;
import com.example.gymapplication.security.dto.SignupRequest;
import com.example.gymapplication.user.RoleRepository;
import com.example.gymapplication.user.UserRepository;
import com.example.gymapplication.user.model.ERole;
import com.example.gymapplication.user.model.Role;
import com.example.gymapplication.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final EmailService emailService;

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void register(SignupRequest signUpRequest) {
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .build();

        Set<String> rolesStr = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (rolesStr == null) {
            Role defaultRole = roleRepository.findByName(ERole.REGULAR_USER)
                    .orElseThrow(() -> new RuntimeException("Cannot find REGULAR USER role"));
            roles.add(defaultRole);
        } else {
            rolesStr.forEach(r -> {
                Role ro = roleRepository.findByName(ERole.valueOf(r))
                        .orElseThrow(() -> new RuntimeException("Cannot find role: " + r));
                roles.add(ro);
            });
        }

        emailService.sendMail(user);

        user.setRoles(roles);
        userRepository.save(user);
    }
}
