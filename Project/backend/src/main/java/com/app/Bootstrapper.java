package com.app;

import com.app.movie.GenreRepository;
import com.app.movie.MovieRepository;
import com.app.movie.MovieService;
import com.app.movie.model.EGenre;
import com.app.movie.model.Genre;
import com.app.movie.model.Movie;
import com.app.security.AuthService;
import com.app.security.dto.SignupRequest;
import com.app.user.RoleRepository;
import com.app.user.UserRepository;
import com.app.user.model.ERole;
import com.app.user.model.Role;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final MovieRepository movieRepository;

    private final GenreRepository genreRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    //change spring.jpa.hibernate.ddl-auto to create
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {

            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("radu@email.com")
                    .username("radu")
                    .password("Parola1!")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("radu1@email.com")
                    .username("radu1")
                    .password("Parola1!")
                    .roles(Set.of("REGULAR_USER"))
                    .build());

            for (EGenre value : EGenre.values()) {
                genreRepository.save(
                        Genre.builder()
                                .name(value)
                                .build()
                );
            }
            getMoviesFromApi();
        }
    }

    private void getMoviesFromApi()  {
        movieRepository.deleteAll();

        String apiKey = "81cf9b91f2e1c5eddc7b5a24ed4da587";

        for (int i = 0; i < 200; i++) {
            Set<Genre> genres = new HashSet<>();

            HttpResponse<String> response = Unirest.get(
                    "https://api.themoviedb.org/3/movie/" + i + "?api_key=" + apiKey
            ).asString();

            JSONObject movieJson = new JSONObject(response.getBody());

            if (movieJson.has("title") && !movieJson.isNull("poster_path") && !movieJson.isNull("backdrop_path")) {

                JSONArray genresJson = movieJson.getJSONArray("genres");

                for (int j = 0; j < genresJson.length(); j++) {
                    JSONObject genre = genresJson.getJSONObject(j);
                    String name = genre.getString("name");
                    String newName = name.replace(' ', '_');

                    Optional<Genre> g = genreRepository.findByName(EGenre.valueOf(newName));
                    g.ifPresent(genres::add);
                }

                Movie movie = Movie.builder()
                        .title(movieJson.getString("title"))
                        .description(movieJson.getString("overview"))
                        .imageLink("https://image.tmdb.org/t/p/original" + movieJson.getString("poster_path"))
                        .backdropLink("https://image.tmdb.org/t/p/original" + movieJson.getString("backdrop_path"))
                        .rating(0.0f)
                        .genres(genres)
                        .build();

                movieRepository.save(movie);
            }
        }
    }
}
