package com.example.youtubeish;

import com.example.youtubeish.security.AuthService;
import com.example.youtubeish.security.dto.SignupRequest;
import com.example.youtubeish.user.RoleRepository;
import com.example.youtubeish.user.UserRepository;
import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.user.model.ERole;
import com.example.youtubeish.user.model.Role;
import com.example.youtubeish.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(UserDTO.builder()
                    .email("rares@email.com")
                    .username("rares")
                    .password("12345678")
                    .roles(Set.of(Role.builder()
                                    .name(ERole.ADMIN)
                            .build()))
                    .build());
            authService.register(UserDTO.builder()
                    .email("rares1@email.com")
                    .username("rares1")
                    .password("12345678")
                    .roles(Set.of(Role.builder()
                            .name(ERole.CUSTOMER)
                            .build()))
                    .build());
        }
    }
}
