package com.example.demo.watchlist;

import com.example.demo.movie.MovieMapper;
import com.example.demo.movie.MovieRepository;
import com.example.demo.movie.dto.MovieDTO;
import com.example.demo.movie.model.Movie;
import com.example.demo.review.ReviewMapper;
import com.example.demo.review.ReviewRepository;
import com.example.demo.review.ReviewService;
import com.example.demo.user.UserRepository;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.example.demo.TestCreationFactory.*;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class WatchlistServiceTest {

    @InjectMocks
    private WatchlistService watchlistService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieMapper movieMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        watchlistService=new WatchlistService(userRepository, movieRepository, movieMapper);
    }

    @Test
    void isInWatchlist(){
        Movie movie=newMovie();
        Long id=randomLong();
        User user=newUser();
        List<Movie> movies=listOf(Movie.class);
        user.setWatchlist(movies);
        user.setId(id);

        assertFalse(watchlistService.isInWatchlist(user,movie));
        movies.add(movie);
        assertTrue(watchlistService.isInWatchlist(user,movie));

    }


    @Test
    void addToWatchlist(){
        Movie movie=newMovie();
        Long id=randomLong();
        User user=newUser();
        List<Movie> movies=listOf(Movie.class);
        user.setWatchlist(movies);
        user.setId(id);

        when(movieRepository.findById(movie.getId())).thenReturn(of(movie));
        when(userRepository.findById(user.getId())).thenReturn(of(user));
        when(userRepository.save(user)).thenReturn(user);
        assertFalse(watchlistService.isInWatchlist(user,movie));
        watchlistService.addToWatchlist(user.getId(),movie.getId());
        assertTrue(watchlistService.isInWatchlist(user,movie));
    }

    @Test
    void removeFromWatchlist(){
        Movie movie=newMovie();
        Long id=randomLong();
        User user=newUser();
        List<Movie> movies=listOf(Movie.class);
        movies.add(movie);
        user.setWatchlist(movies);
        user.setId(id);

        when(movieRepository.findById(movie.getId())).thenReturn(of(movie));
        when(userRepository.findById(user.getId())).thenReturn(of(user));
        when(userRepository.save(user)).thenReturn(user);
        assertTrue(watchlistService.isInWatchlist(user,movie));
        watchlistService.removeFromWatchlist(user.getId(),movie.getId());
        assertFalse(watchlistService.isInWatchlist(user,movie));
    }

    @Test
    void getWatchlist(){
        Long id=randomLong();
        User user=newUser();
        List<Movie> movies=listOf(Movie.class);
        user.setId(id);
        user.setWatchlist(movies);
        Movie movie=newMovie();
        MovieDTO movieDTO=newMovieDTO();

        when(userRepository.findById(id)).thenReturn(of(user));
        when(movieMapper.toDto(movie)).thenReturn(movieDTO);
        List<MovieDTO> movieDTOS = watchlistService.getWatchlist(user.getId());

        assertEquals(movies.size(),movieDTOS.size());

    }
}
