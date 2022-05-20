package com.lab4.demo;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.EGenre;
import com.lab4.demo.book.model.Genre;
import com.lab4.demo.book.repository.BookRepository;
import com.lab4.demo.book.repository.GenreRepository;
import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.SignupRequest;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.repository.RoleRepository;
import com.lab4.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.lab4.demo.book.model.EGenre.*;


@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthService authService;


    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            bookRepository.deleteAll();
            genreRepository.deleteAll();
            for(EGenre value : EGenre.values()) {
                genreRepository.save(
                    Genre.builder()
                    .name(value)
                    .build()
                );
            }
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }

            bookRepository.save(
                Book.builder()
                .title("The Hobbit")
                .author("J.R.R. Tolkien")
                .pages(304)
                .quantity(5)
                .year(1937)
                .description("The Hobbit, or There and Back Again, is a children's fantasy novel written by English author and scholar J. R. R. Tolkien. It is part of the Tolkien Children's Library Project, which was initiated by the BBC in partnership with the American Educational Research Service. It was first published on 21 September 1937 as an EPUB and later as a hardback book in the United States and New Zealand.")
                .genre(genreRepository.findByName(Fantasy))
                .build()
            );
            bookRepository.save(
                Book.builder()
                .title("The Lord of the Rings")
                .author("J.R.R. Tolkien")
                .pages(1288)
                .year(1954)
                .quantity(5)
                .description("The Lord of the Rings is a fantasy novel written by English author and scholar J. R. R. Tolkien. It is one of the two main novels in the epic fantasy canon begun by the brothers Howard and J. R. R. Tolkien. The story began as a sequel to Tolkien's 1937 fantasy novel The Hobbit, but eventually developed into a much larger work. Written in stages between 1937 and 1949, The Lord of the Rings is one of the best-selling novels in the English language, with over 150 million copies sold in its first year alone.")
                .genre(genreRepository.findByName(Fantasy))
                .build()
            );
            bookRepository.save(
                Book.builder()
                .title("The Catcher in the Rye")
                .author("J.D. Salinger")
                .pages(234)
                .year(1951)
                .quantity(5)
                .description("The Catcher in the Rye is a novel by J. D. Salinger. It was published in the United States in 1951 and became an instant bestseller. The novel was inspired by the lives of transient teenagers, especially those who were exposed to the horrors of the working-class South during the Depression.")
                .genre(genreRepository.findByName(Bildungsroman))
                .build()
            );
            bookRepository.save(
                Book.builder()
                .title("The Great Gatsby")
                .author("F. Scott Fitzgerald")
                .pages(208)
                .year(1925)
                .quantity(5)
                .description("The Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional town of West Egg on prosperous Long Island in the summer of 1922. The story primarily concerns the young and mysterious millionaire Jay Gatsby and his quixotic passion for the beautiful Daisy Buchanan, who has moved into the house after the family is separated.")
                .genre(genreRepository.findByName(HistoricalFiction))
                .build()
            );
            bookRepository.save(
                Book.builder()
                .title("The Grapes of Wrath")
                .author("John Steinbeck")
                .pages(464)
                .year(1939)
                .quantity(5)
                .description("The Grapes of Wrath is a novel by John Steinbeck, published in 1939. It was his last published novel and is considered one of his best works. The book is set in the fictional town of Ripley, Maine, and recounts the life of the Grapes of Wrath family.")
                .genre(genreRepository.findByName(HistoricalFiction))
                .build()
            );


            authService.register(SignupRequest.builder()
                    .email("malina@email.com")
                    .username("mxligr")
                    .password("WooHoo1!")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("malina1@email.com")
                    .username("mxligr_customer")
                    .password("WooHoo1!")
                    .roles(Set.of("CUSTOMER"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("malina2@email.com")
                    .username("mxligr_manager")
                    .password("WooHoo1!")
                    .roles(Set.of("MANAGER"))
                    .build());
        }
    }
}
