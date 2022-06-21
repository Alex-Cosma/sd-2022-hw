package com.rdaniel.sd.a2.backend;

import com.rdaniel.sd.a2.backend.book.BookRepository;
import com.rdaniel.sd.a2.backend.book.model.Book;
import com.rdaniel.sd.a2.backend.security.AuthService;
import com.rdaniel.sd.a2.backend.security.dto.SignupRequest;
import com.rdaniel.sd.a2.backend.user.RoleRepository;
import com.rdaniel.sd.a2.backend.user.UserRepository;
import com.rdaniel.sd.a2.backend.user.model.Role;
import com.rdaniel.sd.a2.backend.user.model.RoleType;
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
      for (RoleType roleType : RoleType.values()) {
        roleRepository.save(
            Role.builder()
                .name(roleType)
                .build()
        );
      }

      authService.register(SignupRequest.builder()
          .username("admin")
          .password("admin")
          .email("admin@gmail.com")
          .roles(Set.of("ADMIN"))
          .build()
      );

      authService.register(SignupRequest.builder()
          .username("employee")
          .password("employee")
          .email("employee@gmail.com")
          .roles(Set.of("EMPLOYEE"))
          .build()
      );

      authService.register(SignupRequest.builder()
          .username("employee2")
          .password("employee2")
          .email("employee2@gmail.com")
          .roles(Set.of("EMPLOYEE"))
          .build()
      );

      authService.register(SignupRequest.builder()
          .username("employee3")
          .password("employee3")
          .email("employee3@gmail.com")
          .roles(Set.of("EMPLOYEE"))
          .build()
      );

      bookRepository.save(Book.builder()
          .title("A Game of Thrones")
          .author("George R.R. Martin")
          .genre("Epic Fantasy")
          .quantity(25)
          .price(19.99)
          .build()
      );

      bookRepository.save(Book.builder()
          .title("A Clash of Kings")
          .author("George R.R. Martin")
          .genre("Epic Fantasy")
          .quantity(27)
          .price(19.99)
          .build()
      );

      bookRepository.save(Book.builder()
          .title("East of Eden")
          .author("John Steinbeck")
          .genre("Novel")
          .quantity(32)
          .price(27.32)
          .build()
      );

      bookRepository.save(Book.builder()
          .title("The Pearl")
          .author("John Steinbeck")
          .genre("Novel")
          .quantity(0)
          .price(25.99)
          .build()
      );

      bookRepository.save(Book.builder()
          .title("Lord of the Flies")
          .author("William Golding")
          .genre("Novel")
          .quantity(27)
          .price(21.23)
          .build()
      );

      bookRepository.save(Book.builder()
          .title("Lord of the Rings: The Fellowship of the Ring")
          .author("J.R.R. Tolkien")
          .genre("Epic Fantasy")
          .quantity(27)
          .price(26.50)
          .build()
      );

      bookRepository.save(Book.builder()
          .title("Lord of the Rings: The Return of the King")
          .author("J.R.R. Tolkien")
          .genre("Epic Fantasy")
          .quantity(30)
          .price(26.50)
          .build()
      );

      bookRepository.save(Book.builder()
          .title("Lord of the Rings: The Two Towers")
          .author("J.R.R. Tolkien")
          .genre("Epic Fantasy")
          .quantity(30)
          .price(26.50)
          .build()
      );

      bookRepository.save(Book.builder()
          .title("The Works of Lord Byron")
          .author("Lord Byron")
          .genre("Poetry")
          .quantity(30)
          .price(31.20)
          .build()
      );
    }
  }
}
