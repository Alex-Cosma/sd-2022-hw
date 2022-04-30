package com.assignment2.bookstoreapp;

import com.assignment2.bookstoreapp.book.BookRepository;
import com.assignment2.bookstoreapp.book.BookService;
import com.assignment2.bookstoreapp.book.model.dto.BookDTO;
import com.assignment2.bookstoreapp.security.AuthService;
import com.assignment2.bookstoreapp.security.dto.SignupRequest;
import com.assignment2.bookstoreapp.user.RoleRepository;
import com.assignment2.bookstoreapp.user.UserRepository;
import com.assignment2.bookstoreapp.user.model.ERole;
import com.assignment2.bookstoreapp.user.model.Role;
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

    private final BookRepository bookRepository;

    private final BookService bookService;

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
            bookService.create(new BookDTO(1L, "Pride and Prejudice", "Jane Austen", "ROMANCE",20, 30));
            bookService.create(new BookDTO(1L, "War and Peace", "Lev Tolstoi", "CLASSIC",5, 40));
            authService.register(SignupRequest.builder()
                    .username("alex")
                    .password("WooHoo1!")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .username("alex1")
                    .password("WooHoo1!")
                    .roles(Set.of("EMPLOYEE"))
                    .build());
        }
    }
}