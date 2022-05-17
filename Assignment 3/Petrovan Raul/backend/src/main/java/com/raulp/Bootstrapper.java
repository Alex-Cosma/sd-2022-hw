package com.raulp;

import com.raulp.book.BookRepository;
import com.raulp.book.model.Book;
import com.raulp.book.model.Genre;
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

    private final AuthService authService;

    private final BookRepository bookRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            bookRepository.deleteAll();
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
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("alex1@email.com")
                    .username("alex1")
                    .password("WooHoo1!")
                    .roles(Set.of("STUDENT"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("alex2@email.com")
                    .username("alex2")
                    .password("WooHoo1!")
                    .roles(Set.of(ERole.FLIGHT_INSTRUCTOR.toString()))
                    .build());
            bookRepository.save(
                    Book.builder().author("John").description("best book").genre(Genre.ACTION)
                            .title("The Happening").price((float) 22.3).quantity(1).build());
            bookRepository.save(
                    Book.builder().author("John").description("best book 2").genre(Genre.DRAMA)
                            .title("The Happening").price((float) 23.5).quantity(0).build());

        }
    }
}
