package com.example.bookstore;

import com.example.bookstore.Report.ReportServiceFactory;
import com.example.bookstore.book.BookController;
import com.example.bookstore.book.BookMapper;
import com.example.bookstore.book.BookRepository;
import com.example.bookstore.book.BookService;
import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.Genre;
import com.example.bookstore.security.AuthController;
import com.example.bookstore.security.AuthService;
import com.example.bookstore.security.dto.LoginRequest;
import com.example.bookstore.security.dto.SignupRequest;
import com.example.bookstore.user.RoleRepository;
import com.example.bookstore.user.UserController;
import com.example.bookstore.user.UserRepository;
import com.example.bookstore.user.model.ERole;
import com.example.bookstore.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.example.bookstore.book.model.Genre.FANTASY;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final ReportServiceFactory reportServiceFactory;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final BookRepository bookRepository;

    private final AuthService authService;

    private final AuthController authController;

    private final BookMapper bookMapper;
    private BookService bookService;
    private BookController bookController;


    @Value("${app.bootstrap}")
    private Boolean bootstrap;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        bookService = new BookService(reportServiceFactory,bookRepository,bookMapper);
        bookController = new BookController(bookService);
        if(bootstrap) {
            System.out.println("here");
            userRepository.deleteAll();
            roleRepository.deleteAll();
            bookRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .role(value)
                                .build()
                );
            }
            final Book book = Book.builder().title("Two Towers").author("JRRT").genre("FANTASY").quantity(0).price(2).build();
            final Book book1 = Book.builder().title("Fellowship").author("JRRT").genre("ACTION").quantity(100).price(2000).build();
            bookRepository.save(book);
            bookRepository.save(book1);
            authService.register(SignupRequest.builder()
                    .email("achi.grigore@egmail.com")
                    .username("axlsage")
                    .password("RasenganSage12*")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("notemail@ongod.com")
                    .username("sasukenerfed")
                    .password("kamehameha2*")
                    .roles(Set.of("REGULAR"))
                    .build());
        }
    }
}
