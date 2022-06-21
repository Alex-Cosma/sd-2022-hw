package com.example.demo.movie;

import com.example.demo.TestCreationFactory;
import com.example.demo.movie.dto.MovieDTO;
import com.example.demo.movie.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.TestCreationFactory.*;
import static com.example.demo.TestCreationFactory.newMovieDTO;
import static com.mongodb.assertions.Assertions.assertFalse;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
public class MovieServiceIntegrationTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        movieRepository.deleteAll();
    }

    @Test
    void findAll(){
        List<Movie> movies=listOf(Movie.class);
        movieRepository.saveAll(movies);
        List<MovieDTO> foundMovies=movieService.findAll();
        assertEquals(movies.size(),foundMovies.size());
    }

    @Test
    void findByFilter(){
        List<Movie> movies = listOf(Movie.class);
        List<Movie> filteredMovies=new ArrayList<>();
        int i=0;
        for(Movie movie: movies){
            if(i<movies.size()/2){
                movie.setTitle("random"+i);
                filteredMovies.add(movie);
            }
            i++;
        }
        movieRepository.saveAll(movies);
        List<MovieDTO> all = movieService.findAllByFilter("random");

        assertEquals(filteredMovies.size(), all.size());
    }

    @Test
    void findMovie(){
        Movie movie=newMovie();
        Movie savedMovie=movieRepository.save(movie);
        MovieDTO movieDTO=movieService.findMovie(savedMovie.getId());
        assertEquals(movie.getTitle(),movieDTO.getTitle());
    }

    @Test
    void create(){
        assertEquals(0,movieRepository.findAll().size());
        MovieDTO movieDTO=newMovieDTO();
        MovieDTO newMovieDTO=movieService.create(movieDTO);
        assertEquals(movieDTO.getTitle(),newMovieDTO.getTitle());
        assertEquals(1,movieRepository.findAll().size());
    }

    @Test
    void delete(){
        MovieDTO movie=newMovieDTO();
        MovieDTO savedMovie=movieService.create(movie);
        movieService.delete(savedMovie.getId());
        assertFalse(movieRepository.existsById(savedMovie.getId()));
    }

    @Test
    void update(){

        MovieDTO movie=newMovieDTO();

        MovieDTO savedMovie=movieService.create(movie);
        savedMovie.setTitle("New Title");
        movieService.update(savedMovie.getId(), savedMovie);
        MovieDTO updatedMovie=movieService.update(savedMovie.getId(),savedMovie);
        assertEquals(savedMovie, updatedMovie);

    }

}
