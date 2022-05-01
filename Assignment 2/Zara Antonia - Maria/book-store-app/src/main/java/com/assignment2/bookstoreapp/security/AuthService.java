package com.assignment2.bookstoreapp.security;

import com.assignment2.bookstoreapp.security.dto.SignupRequest;
import com.assignment2.bookstoreapp.user.RoleRepository;
import com.assignment2.bookstoreapp.user.UserRepository;
import com.assignment2.bookstoreapp.user.dto.UserRegisterDTO;
import com.assignment2.bookstoreapp.user.model.ERole;
import com.assignment2.bookstoreapp.user.model.Role;
import com.assignment2.bookstoreapp.user.model.User;
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


    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public void register(SignupRequest signUpRequest) {
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .build();

        Set<String> rolesStr = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (rolesStr == null) {
            Role defaultRole = roleRepository.findByName(ERole.EMPLOYEE)
                    .orElseThrow(() -> new RuntimeException("Cannot find EMPLOYEE role"));
            roles.add(defaultRole);
        } else {
            rolesStr.forEach(r -> {
                Role ro = roleRepository.findByName(ERole.valueOf(r))
                        .orElseThrow(() -> new RuntimeException("Cannot find role: " + r));
                roles.add(ro);
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
    }

    public void createNewUserFromDTO(UserRegisterDTO user) {
        register(new SignupRequest(user.getName(), user.getPassword(), user.getRoles()));
    }
}