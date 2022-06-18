package com.app.bookingapp.security;

import com.app.bookingapp.data.sql.entity.Role;
import com.app.bookingapp.data.sql.entity.User;
import com.app.bookingapp.data.sql.entity.enums.ERole;
import com.app.bookingapp.data.sql.repo.RoleRepository;
import com.app.bookingapp.data.sql.repo.UserRepository;
import com.app.bookingapp.security.dto.SignupRequest;
//import project.booking.user.RoleRepository;
//import project.booking.user.UserRepository;
//import project.booking.user.model.ERole;
//import project.booking.user.model.Role;
//import project.booking.user.model.User;
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

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void register(SignupRequest signUpRequest) {
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .build();

        //Set<String> rolesStr = signUpRequest.getRoles();
        Set<String> rolesStr = null;
        Set<Role> roles = new HashSet<>();

        if (signUpRequest.getAdmin()){
            rolesStr = new HashSet<>();
            rolesStr.add(ERole.ADMIN.toString());
        }

        if (rolesStr == null) {
            Role defaultRole = roleRepository.findByName(ERole.CLIENT)
                    .orElseThrow(() -> new RuntimeException("Cannot find EMPLOYEE role"));
            user.setRole(defaultRole);  //modified
        } else {
            rolesStr.forEach(r -> {
                Role ro = roleRepository.findByName(ERole.valueOf(r))
                        .orElseThrow(() -> new RuntimeException("Cannot find role: " + r));
                user.setRole(ro);      //modified
            });
        }

        userRepository.save(user);

//        if (rolesStr == null) {
//            Role defaultRole = roleRepository.findByName(ERole.CLIENT)
//                    .orElseThrow(() -> new RuntimeException("Cannot find EMPLOYEE role"));
//            roles.add(defaultRole);
//        } else {
//            rolesStr.forEach(r -> {
//                Role ro = roleRepository.findByName(ERole.valueOf(r))
//                        .orElseThrow(() -> new RuntimeException("Cannot find role: " + r));
//                roles.add(ro);
//            });
//        }
//
//        user.(roles);
//        userRepository.save(user);
    }
}
