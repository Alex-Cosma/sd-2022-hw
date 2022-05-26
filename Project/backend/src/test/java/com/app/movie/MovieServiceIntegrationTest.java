package com.app.movie;

import com.app.TestCreationFactory;
import com.app.movie.model.EGenre;
import com.app.movie.model.Genre;
import com.app.movie.model.Movie;
import com.app.movie.model.dto.MovieDTO;
import com.app.movie.model.mapper.MovieMapper;
import com.app.review.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class MovieServiceIntegrationTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MovieMapper movieMapper;

    @BeforeEach
    void setUp() {
        movieRepository.deleteAll();
        genreRepository.deleteAll();
    }

    @Test
    void allMovies() {
        List<Movie> movies = TestCreationFactory.listOf(Movie.class);
        movieRepository.saveAll(movies);

        List<MovieDTO> movieDTOS = movieService.allMovies();

        assertEquals(movies.size(), movieDTOS.size());
    }

    @Test
    void getGenresStr() {
        Set<Genre> genres = new HashSet<>();
        genres.add(Genre.builder().name(EGenre.Action).build());
        genres.add(Genre.builder().name(EGenre.Comedy).build());

        String str = "Comedy Action ";
        String genresStr = movieService.getGenresStr(genres);

        String[] split = genresStr.split(" ");

        assertEquals(2, split.length);
    }

    @Test
    void findById() {
        Movie movie = TestCreationFactory.newMovie();
        Movie savedMovie = movieRepository.save(movie);

        MovieDTO movieDTO = movieService.findById(savedMovie.getId());

        assertEquals(movie.getTitle(), movieDTO.getTitle());
    }

    @Test
    void getWatchlistMovies() {
        List<Movie> movies = TestCreationFactory.listOf(Movie.class);
        movieRepository.saveAll(movies);

        List<Long> ids = new ArrayList<>();
        List<Movie> allMovies = movieRepository.findAll();

        ids.add(allMovies.get(0).getId());
        ids.add(allMovies.get(1).getId());

        List<MovieDTO> movieDTOS = movieService.getWatchlistMovies(ids);

        assertEquals(2, movieDTOS.size());
    }

    @Test
    void create() {
        MovieDTO movieDTO = TestCreationFactory.newMovieDTO();
        MovieDTO savedMovie = movieService.create(movieDTO);

        assertEquals(movieDTO.getTitle(), savedMovie.getTitle());
    }

    @Test
    void edit() {
        Movie movie = TestCreationFactory.newMovie();
        Movie savedMovie = movieRepository.save(movie);

        MovieDTO movieDTO = movieMapper.toDto(savedMovie);
        movieDTO.setGenresString("Action Adventure");
        movieDTO.setTitle("New title");

        MovieDTO edited = movieService.edit(movieDTO);

        assertEquals(movieDTO.getTitle(), edited.getTitle());
    }

    @Test
    void getGenres() {
        genreRepository.save(Genre.builder().name(EGenre.Adventure).build());
        genreRepository.save(Genre.builder().name(EGenre.Comedy).build());
        genreRepository.save(Genre.builder().name(EGenre.Drama).build());

        String genresString = "Adventure Comedy Drama";
        Set<Genre> genres = movieService.getGenres(genresString);

        assertEquals(3, genres.size());
    }

    @Test
    void delete() {
        Movie movie = TestCreationFactory.newMovie();
        Movie savedMovie = movieRepository.save(movie);

        movieService.delete(savedMovie.getId());

        Optional<Movie> m = movieRepository.findById(savedMovie.getId());

        assertFalse(m.isPresent());
    }
}
