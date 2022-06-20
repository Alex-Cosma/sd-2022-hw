package com.example.gymapplication;

import com.example.gymapplication.security.AuthService;
import com.example.gymapplication.security.dto.SignupRequest;
import com.example.gymapplication.training.LocationRepository;
import com.example.gymapplication.training.TrainingRepository;
import com.example.gymapplication.training.model.Location;
import com.example.gymapplication.user.RoleRepository;
import com.example.gymapplication.user.UserRepository;
import com.example.gymapplication.user.model.ERole;
import com.example.gymapplication.user.model.Role;
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

    private final TrainingRepository trainingRepository;

    private final LocationRepository locationRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            trainingRepository.deleteAll();
            userRepository.deleteAll();
            locationRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }

            locationRepository.save(
                    Location.builder()
                            .address("Calea Turzii 178")
                            .build()
            );

            locationRepository.save(
                    Location.builder()
                            .address("Campului 87")
                            .build()
            );

            locationRepository.save(
                    Location.builder()
                            .address("Observatorului 82")
                            .build()
            );


            authService.register(SignupRequest.builder()
                    .email("admin@email.com")
                    .username("admin")
                    .password("WooHoo1!")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("trainer@email.com")
                    .username("trainer")
                    .password("WooHoo1!")
                    .roles(Set.of("TRAINER"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("regular_user@email.com")
                    .username("regular_user")
                    .password("WooHoo1!")
                    .roles(Set.of("REGULAR_USER"))
                    .build());
        }
    }
}
