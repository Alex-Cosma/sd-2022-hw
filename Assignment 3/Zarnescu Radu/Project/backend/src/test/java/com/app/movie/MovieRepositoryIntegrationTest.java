package com.app.movie;

import com.app.TestCreationFactory;
import com.app.movie.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MovieRepositoryIntegrationTest {

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        movieRepository.deleteAll();
    }

    @Test
    void findAllIds() {
        List<Movie> movies = TestCreationFactory.listOf(Movie.class);
        List<Movie> savedMovies = movieRepository.saveAll(movies);

        List<Long> ids = new ArrayList<>();
        ids.add(savedMovies.get(0).getId());
        ids.add(savedMovies.get(1).getId());

        assertEquals(2, movieRepository.findAllIds(ids).size());
    }
}
