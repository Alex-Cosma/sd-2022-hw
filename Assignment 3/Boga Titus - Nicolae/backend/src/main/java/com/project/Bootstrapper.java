package com.project;

import com.project.security.AuthService;
import com.project.security.dto.SignupRequest;
import com.project.song.SongRepository;
import com.project.user.RoleRepository;
import com.project.user.UserRepository;
import com.project.user.model.ERole;
import com.project.user.model.Role;
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

    private final SongRepository songRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            songRepository.deleteAll();
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
                    .email("titus@email.com")
                    .username("titus")
                    .password("PasswordPass1")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("titus1@email.com")
                    .username("titus1")
                    .password("PasswordPass1")
                    .roles(Set.of("CUSTOMER"))
                    .build());
        }
    }
}
