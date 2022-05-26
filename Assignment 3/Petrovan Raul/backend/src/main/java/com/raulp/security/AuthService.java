package com.raulp.security;

import com.raulp.security.dto.SignupRequest;
import com.raulp.user.model.Instructor;
import com.raulp.user.model.Student;
import com.raulp.user.repos.InstructorRepository;
import com.raulp.user.repos.RoleRepository;
import com.raulp.user.repos.StudentRepository;
import com.raulp.user.repos.UserRepository;
import com.raulp.user.model.ERole;
import com.raulp.user.model.Role;
import com.raulp.user.model.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;


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
            Role defaultRole = roleRepository.findByName(ERole.STUDENT)
                    .orElseThrow(() -> new RuntimeException("Cannot find STUDENT role"));
            roles.add(defaultRole);
        } else {
            rolesStr.forEach(r -> {
                Role ro = roleRepository.findByName(ERole.valueOf(r))
                        .orElseThrow(() -> new RuntimeException("Cannot find role: " + r));
                roles.add(ro);
            });
        }

        user.setRoles(roles);
        if (rolesStr != null && rolesStr.contains(ERole.FLIGHT_INSTRUCTOR.toString())) {
            instructorRepository.save(Instructor.builder().username(signUpRequest.getUsername())
                    .password(encoder.encode(signUpRequest.getPassword()))
                    .email(signUpRequest.getEmail())
                    .roles(user.getRoles()).build());
        } else if (rolesStr != null && rolesStr.contains(ERole.STUDENT.toString())) {
            studentRepository.save(Student.builder().username(signUpRequest.getUsername())
                    .password(encoder.encode(signUpRequest.getPassword()))
                    .email(signUpRequest.getEmail())
                    .roles(user.getRoles()).build());
        } else {
            userRepository.save(user);
        }
    }
}
