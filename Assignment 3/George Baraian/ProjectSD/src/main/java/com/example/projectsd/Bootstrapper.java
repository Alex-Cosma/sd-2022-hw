package com.example.projectsd;

import com.example.projectsd.court.CourtRepository;
import com.example.projectsd.security.AuthService;
import com.example.projectsd.security.dto.SignupRequest;
import com.example.projectsd.user.RoleRepository;
import com.example.projectsd.user.UserRepository;
import com.example.projectsd.user.model.ERole;
import com.example.projectsd.user.model.Role;
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

    private final CourtRepository courtRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            courtRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("george@gmail.com")
                    .username("george")
                    .password("Masina10!")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("george1@gmail.com")
                    .username("george1")
                    .password("Masina20!")
                    .roles(Set.of("CLIENT"))
                    .build());;
        }
    }
}
