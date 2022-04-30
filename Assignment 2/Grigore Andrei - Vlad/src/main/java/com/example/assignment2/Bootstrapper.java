package com.example.assignment2;

import com.example.assignment2.bookstore.BookController;
import com.example.assignment2.bookstore.BookRepository;
import com.example.assignment2.bookstore.model.Book;
import com.example.assignment2.reports.ReportType;
import com.example.assignment2.security.AuthController;
import com.example.assignment2.security.AuthService;
import com.example.assignment2.security.dto.LoginRequest;
import com.example.assignment2.security.dto.SignupRequest;
import com.example.assignment2.user.RoleRepository;
import com.example.assignment2.user.UserRepository;
import com.example.assignment2.user.model.ERole;
import com.example.assignment2.user.model.Role;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final BookRepository bookRepository;
    private final AuthController authController;
    private final BookController bookController;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(bootstrap) {
            bookRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .role(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("andrei2@gmail.com")
                    .username("adolf2")
                    .password("parolaHei1!")
                    .roles(Set.of("ADMINISTRATOR"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("andrei3@gmail.com")
                    .username("adolf3")
                    .password("parolaHei2!")
                    .roles(Set.of("EMPLOYEE"))
                    .build());
            bookRepository.save(Book.builder()
                    .author("Someone else")
                    .genre("Dank Memes")
                    .quantity(0)
                    .title("Doom")
                    .price(10)
                    .build());
            bookRepository.save(Book.builder()
                    .author("Damn Daniel")
                    .genre("Dank Memes")
                    .quantity(0)
                    .title("TrueStory2")
                    .price(20)
                    .build());
        }
    }
}
