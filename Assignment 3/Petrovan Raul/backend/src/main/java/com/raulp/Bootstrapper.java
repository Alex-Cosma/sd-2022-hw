package com.raulp;

import com.raulp.flight.Airport;
import com.raulp.flight.Plane;
import com.raulp.flight.repos.AirportRepository;
import com.raulp.flight.repos.PlaneRepository;
import com.raulp.security.AuthService;
import com.raulp.security.dto.SignupRequest;
import com.raulp.user.repos.InstructorRepository;
import com.raulp.user.repos.RoleRepository;
import com.raulp.user.repos.StudentRepository;
import com.raulp.user.repos.UserRepository;
import com.raulp.user.model.ERole;
import com.raulp.user.model.Role;
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
    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository;
    private final AirportRepository airportRepository;
    private final PlaneRepository planeRepository;

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
            authService.register(SignupRequest.builder()
                    .email("alex@email.com")
                    .username("alex")
                    .password("WooHoo1!")
                    .roles(Set.of(ERole.ADMIN.toString()))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("alex1@email.com")
                    .username("alex1")
                    .password("WooHoo1!")
                    .roles(Set.of(ERole.STUDENT.toString()))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("alex2@email.com")
                    .username("alex2")
                    .password("WooHoo1!")
                    .roles(Set.of(ERole.FLIGHT_INSTRUCTOR.toString()))
                    .build());

            airportRepository.save(Airport.builder().name("Baia Mare").icao("LRBM").build());
            airportRepository.save(Airport.builder().name("Bucharest Otopeni").icao("LROP").build());
            airportRepository.save(Airport.builder().name("Bucharest Baneasa").icao("LRBS").build());

            planeRepository.save(Plane.builder().name("Boeing 737").build());
            planeRepository.save(Plane.builder().name("Boeing 747").build());
            planeRepository.save(Plane.builder().name("Boeing 767").build());
            planeRepository.save(Plane.builder().name("Boeing 777").build());

        }
    }
}
