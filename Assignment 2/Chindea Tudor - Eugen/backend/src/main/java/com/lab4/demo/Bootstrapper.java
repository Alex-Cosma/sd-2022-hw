package com.lab4.demo;

import com.lab4.demo.book.BookRepository;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import com.lab4.demo.book.model.Book;
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

//import java.net.http.HttpResponse;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final BookRepository bookRepository;

    @Value("true")
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
            HttpResponse<String> response = Unirest.get("https://google-books.p.rapidapi.com/volumes?key=AIzaSyALqJZpoMChMBbrG5Sc3lBd6TtA01hZy3U")
                    .header("X-RapidAPI-Host", "google-books.p.rapidapi.com")
                    .header("X-RapidAPI-Key", "a8b33113c9msh0feed45ddb3ae79p103a13jsned6cc596638e")
                    .asString();
            String booksString = response.getBody();
            JSONObject booksObj = new JSONObject(booksString);
            JSONArray booksJson = booksObj.getJSONArray("items");
            System.out.println("merge");
            for (int i = 0; i < booksJson.length(); i++) {
                JSONObject book = booksJson.getJSONObject(i);
                String title = "";
                String author = "";
                title = book.getJSONObject("volumeInfo").getString("title");
                if(book.getJSONObject("volumeInfo").has("authors")){

                    author =book.getJSONObject("volumeInfo").getJSONArray("authors").get(0).toString();
                }
                bookRepository.save(Book.builder().title(title)
                        .author(author)
                        .genre("gen3")
                        .price(100)
                        .quantity(10).build())
                ;

            }
            bookRepository.save(Book.builder().title("titlu1")
                    .author("auth")
                    .genre("gen3")
                    .price(90)
                    .quantity(60).build());
            bookRepository.save(Book.builder().title("titlu2")
                    .author("auth2")
                    .genre("gen2")
                    .price(90)
                    .quantity(60).build());
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
                    .roles(Set.of("EMPLOYEE"))
                    .build());
        }
    }
}
