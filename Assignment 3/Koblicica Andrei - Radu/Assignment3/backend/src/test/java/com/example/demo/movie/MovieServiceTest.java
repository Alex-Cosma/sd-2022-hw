package com.example.demo.movie;

import com.example.demo.TestCreationFactory;
import com.example.demo.movie.dto.MovieDTO;
import com.example.demo.movie.model.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.TestCreationFactory.*;
import static com.mongodb.assertions.Assertions.assertFalse;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieMapper movieMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        movieService=new MovieService(movieRepository, movieMapper);
    }

    @Test
    void findAll() {
        List<Movie> movies = TestCreationFactory.listOf(Movie.class);
        when(movieRepository.findAll()).thenReturn(movies);

        List<MovieDTO> all = movieService.findAll();

        assertEquals(movies.size(), all.size());
    }


    @Test
    void findAllByFilter() {
        List<Movie> movies = TestCreationFactory.listOf(Movie.class);
        when(movieRepository.findAllByTitleLike("%random%")).thenReturn(movies);

        List<MovieDTO> all = movieService.findAllByFilter("random");

        assertEquals(movies.size(), all.size());
    }

    @Test
    void findMovie(){
        long movieId=randomLong();
        Movie movie=newMovie();
        when(movieRepository.findById(movieId)).thenReturn(of(movie));

        MovieDTO movieDTO=newMovieDTO();
        movieDTO.setId(movie.getId());
        when(movieMapper.toDto(movie)).thenReturn(movieDTO);

        MovieDTO foundMovie = movieService.findMovie(movieId);

        assertEquals(movie.getId(),foundMovie.getId());

    }

    @Test
    void create(){
        Movie movie=newMovie();
        MovieDTO movieDTO=newMovieDTO();
        when(movieMapper.toDto(movie)).thenReturn(movieDTO);
        when(movieMapper.fromDto(movieDTO)).thenReturn(movie);
        when(movieRepository.save(movie)).thenReturn(movie);

        MovieDTO newMovieDTO=movieService.create(movieDTO);
        assertEquals(movieDTO,newMovieDTO);
    }

    @Test
    void delete(){
        Movie movie=newMovie();
        when(movieRepository.save(movie)).thenReturn(movie);
        movieService.delete(movie.getId());
        assertFalse(movieRepository.existsById(movie.getId()));
    }

    @Test
    void update(){
        Movie movie=newMovie();
        MovieDTO movieDTO=newMovieDTO();

        when(movieRepository.findById(movie.getId())).thenReturn(of(movie));
        when(movieRepository.save(movie)).thenReturn(movie);
        when(movieMapper.toDto(movie)).thenReturn(movieDTO);

        MovieDTO newMovieDTO=movieService.update(movie.getId(),movieDTO);
        assertEquals(movieDTO, newMovieDTO);

    }
}
