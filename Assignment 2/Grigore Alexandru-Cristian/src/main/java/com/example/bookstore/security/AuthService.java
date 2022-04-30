package com.example.bookstore.security;

import com.example.bookstore.security.dto.SignupRequest;
import com.example.bookstore.user.RoleRepository;
import com.example.bookstore.user.UserRepository;
import com.example.bookstore.user.model.ERole;
import com.example.bookstore.user.model.Role;
import com.example.bookstore.user.model.User;
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

    private final PasswordEncoder passwordEncoder;

    public boolean existsByUsername(String username){ return userRepository.existsByUsername(username); }

    public boolean existsByEmail(String email){ return userRepository.existsByEmail(email); }

    public void register(SignupRequest signupRequest){
        User user = User.builder()
                .username(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .email(signupRequest.getEmail())
                .build();
        Set<String> rolesStr = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (rolesStr == null) {
            Role defaultRole = roleRepository.findByRole(ERole.REGULAR)
                    .orElseThrow(() -> new RuntimeException("Cannot find REGULAR role"));
            roles.add(defaultRole);
        } else {
            rolesStr.forEach(r -> {
                Role ro = roleRepository.findByRole(ERole.valueOf(r))
                        .orElseThrow(() -> new RuntimeException("Cannot find role: " + r));
                roles.add(ro);
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
    }
}
