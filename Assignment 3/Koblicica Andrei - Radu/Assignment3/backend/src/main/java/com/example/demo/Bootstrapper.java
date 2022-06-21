package com.example.demo;

import com.example.demo.movie.MovieRepository;
import com.example.demo.movie.model.Movie;
import com.example.demo.security.AuthService;
import com.example.demo.security.dto.SignupRequest;
import com.example.demo.user.RoleRepository;
import com.example.demo.user.UserRepository;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {
    @Value("${app.bootstrap}")
    private Boolean bootstrap;


    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final MovieRepository movieRepository;
    @SneakyThrows
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            movieRepository.deleteAll();
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
                    .email("andrei@email.com")
                    .username("andrei")
                    .password("WooHoo1!")
                    .roles(Set.of("ADMIN"))
                    .watchlist(new ArrayList<>())
                    .build());
            authService.register(SignupRequest.builder()
                    .email("theea@email.com")
                    .username("theea")
                    .password("WooHoo1!")
                    .roles(Set.of("REGULAR_USER"))
                    .watchlist(new ArrayList<>())
                    .build());
            authService.register(SignupRequest.builder()
                    .email("alex@email.com")
                    .username("alex")
                    .password("WooHoo1!")
                    .roles(Set.of("REGULAR_USER"))
                    .watchlist(new ArrayList<>())
                    .build());

            for(int i=100;i<300;i++){
                try{
                JSONObject obj = new JSONObject(IOUtils.toString(new URL("https://api.themoviedb.org/3/movie/"+i+"?api_key=0697ef2d8b8e87657b8f553cd124c33b"), Charset.forName("UTF-8")));

                    JSONArray arr = obj.getJSONArray("genres");
                    movieRepository.save(Movie.builder()
                            .title(obj.getString("title"))
                            .description(obj.getString("overview"))
                            .genre(arr.getJSONObject(0).getString("name"))
                            .year(Integer.parseInt(obj.getString("release_date").substring(0,4)))
                            .rating(obj.getFloat("vote_average"))
                            .numberOfReviews(obj.getInt("vote_count"))
                            .imagePath("https://image.tmdb.org/t/p/w500"+obj.getString("poster_path"))
                            .build());
                }
                catch(FileNotFoundException | JSONException ignored){

                }


            }









        }

    }


}
