package com.project.clinic.security;

import com.project.clinic.security.dto.SignupRequest;
import com.project.clinic.skin_color.SkinColorService;
import com.project.clinic.skin_color.model.ESkinColor;
import com.project.clinic.user.RoleRepository;
import com.project.clinic.skin_color.SkinColorRepository;
import com.project.clinic.user.UserRepository;
import com.project.clinic.user.dto.UserRegisterDTO;
import com.project.clinic.user.model.*;
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

    private final SkinColorService skinColorService;

    private final PasswordEncoder encoder;


    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public void register(SignupRequest signUpRequest) {
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .skinColor(skinColorService.findById(signUpRequest.getSkinColorId()))
                .treatments(signUpRequest.getTreatments())
                .points(0)
                .build();

        Set<String> rolesStr = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (rolesStr == null) {
            Role defaultRole = roleRepository.findByName(ERole.CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Cannot find CUSTOMER role"));
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
        register(new SignupRequest(
                user.getName(),
                user.getPassword(),
                skinColorService.findByName(ESkinColor.valueOf(user.getSkinColor())).getId(),
                user.getRoles(),
                new HashSet<>()
                ));
    }
}