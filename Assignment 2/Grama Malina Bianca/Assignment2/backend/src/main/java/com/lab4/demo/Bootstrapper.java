package com.lab4.demo;

import com.lab4.demo.frontoffice.BookRepository;
import com.lab4.demo.frontoffice.model.Book;
import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.SignupRequest;
import com.lab4.demo.user.RoleRepository;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
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
                    .email("malina@email.com")
                    .username("mxligr")
                    .password("Woohoo1!")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("malina1@email.com")
                    .username("mxligr1")
                    .password("Woohoo1!")
                    .roles(Set.of("EMPLOYEE"))
                    .build());
            Book book = Book.builder().title("The Lord of the Rings").author("J.R.R. Tolkien").genre("Fantasy").price(30).quantity(5).build();
            bookRepository.save(book);
            Book book1 = Book.builder().title("The Hobbit").author("J.R.R. Tolkien").genre("Fantasy").price(20).quantity(2).build();
            bookRepository.save(book1);
            Book book2 = Book.builder().title("The Catcher in the Rye").author("J. D. Salinger").genre("Bildungsroman").price(15).quantity(3).build();
            bookRepository.save(book2);
        }
    }
}
