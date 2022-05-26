package com.app.movie;

import com.app.TestCreationFactory;
import com.app.movie.model.EGenre;
import com.app.movie.model.Genre;
import com.app.movie.model.Movie;
import com.app.movie.model.dto.MovieDTO;
import com.app.movie.model.mapper.MovieMapper;
import com.app.review.ReviewService;
import com.app.watchlist.WatchlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private MovieMapper movieMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movieService = new MovieService(movieRepository, genreRepository, movieMapper);
    }

    MovieDTO toDto(Movie movie) {
        return MovieDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .backdropLink(movie.getBackdropLink())
                .imageLink(movie.getImageLink())
                .genres(movie.getGenres())
                .rating(movie.getRating())
                .build();
    }

    @Test
    void allMovies() {
        List<Movie> movies = TestCreationFactory.listOf(Movie.class);

        when(movieRepository.findAll()).thenReturn(movies);
        for (Movie movie : movies) {
            when(movieMapper.toDto(movie)).thenReturn(toDto(movie));
        }

        List<MovieDTO> all = movieService.allMovies();

        assertEquals(movies.size(), all.size());
    }

    @Test
    void findById() {
        Movie movie = TestCreationFactory.newMovie();
        movieRepository.save(movie);

        when(movieRepository.findById(movie.getId())).thenReturn(Optional.of(movie));
        when(movieService.findById(movie.getId())).thenReturn(toDto(movie));

        MovieDTO movieDTO = movieService.findById(movie.getId());
        assertEquals(movie.getId(), movieDTO.getId());

        movieRepository.delete(movie);

        movieDTO = movieService.findById(TestCreationFactory.randomLong());

        assertNull(movieDTO);
    }

    @Test
    void getWatchlistMovies() {
        List<Movie> movies = TestCreationFactory.listOf(Movie.class);
        List<MovieDTO> watchlistMoviesDTOs = new ArrayList<>();
        List<Movie> watchlistMovies = new ArrayList<>();
        watchlistMoviesDTOs.add(toDto(movies.get(0)));
        watchlistMovies.add(movies.get(0));
        watchlistMoviesDTOs.add(toDto(movies.get(1)));
        watchlistMovies.add(movies.get(1));
        watchlistMoviesDTOs.add(toDto(movies.get(2)));
        watchlistMovies.add(movies.get(2));

        List<Long> ids = new ArrayList<>(List.of(movies.get(0).getId(), movies.get(1).getId(), movies.get(2).getId()));

        when(movieRepository.findAllIds(ids)).thenReturn(watchlistMovies);
        for(Movie m: watchlistMovies) {
            when(movieMapper.toDto(m)).thenReturn(toDto(m));
        }
        List<MovieDTO> queryMovies = movieService.getWatchlistMovies(ids);

        assertEquals(watchlistMoviesDTOs, queryMovies);
    }

    @Test
    void create() {
        Movie movie = TestCreationFactory.newMovie();
        MovieDTO movieDTO = toDto(movie);
        movieDTO.setGenresString(movieService.getGenresStr(movie.getGenres()));

        when(movieMapper.toDto(movieRepository.save(movie))).thenReturn(movieDTO);

        MovieDTO createdMovie = movieService.create(movieDTO);

        assertEquals(movieDTO, createdMovie);
    }

    @Test
    void edit() {
        Movie movie = TestCreationFactory.newMovie();
        movieRepository.save(movie);
        MovieDTO movieDTO = toDto(movie);
        movieDTO.setTitle("Edited Title");
        movieDTO.setGenresString(movieService.getGenresStr(movie.getGenres()));

        when(movieMapper.toDto(movieRepository.save(movie))).thenReturn(movieDTO);

        MovieDTO editedMovie = movieService.create(movieDTO);

        assertEquals(movieDTO.getTitle(), editedMovie.getTitle());
        assertNotEquals(movie.getTitle(), editedMovie.getTitle());
    }

    @Test
    void delete() {
        Movie movie = TestCreationFactory.newMovie();
        movieRepository.save(movie);
        movieService.delete(movie.getId());

        Optional<Movie> deletedMovie = movieRepository.findById(movie.getId());

        assertFalse(deletedMovie.isPresent());
    }

    @Test
    void getGenresStr() {
        Movie movie = TestCreationFactory.newMovie();
        Set<Genre> genres = movie.getGenres();
        Genre[] genresArray = genres.toArray(new Genre[0]);
        StringBuilder str = new StringBuilder();
        for (Genre genre : genresArray) {
            str.append(genre.getName()).append(" ");
        }

        String genresStr = movieService.getGenresStr(genres);

        assertEquals(str.toString(), genresStr);
    }

    @Test
    void getGenres() {
        String genresString = "Action Adventure Comedy";
        String[] split = genresString.split(" ");
        Set<Genre> genres = new HashSet<>();
        for(String s: split) {
            Genre genre = Genre.builder().name(EGenre.valueOf(s)).build();
            genres.add(genre);

            when(genreRepository.findByName(EGenre.valueOf(s))).thenReturn(Optional.of(genre));
        }

        Set<Genre> toTest = movieService.getGenres(genresString);

        assertEquals(genres, toTest);
    }
}
