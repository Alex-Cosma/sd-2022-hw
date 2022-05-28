package com.app.movie;

import com.app.movie.model.EGenre;
import com.app.movie.model.Genre;
import com.app.movie.model.Movie;
import com.app.movie.model.dto.MovieDTO;
import com.app.movie.model.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    private final GenreRepository genreRepository;

    private final MovieMapper movieMapper;

    public List<MovieDTO> allMovies() {
        List<MovieDTO> movies = movieRepository.findAll().stream()
                .map(movieMapper::toDto)
                .collect(Collectors.toList());
        for (MovieDTO m : movies) {
            m.setGenresString(getGenresStr(m.getGenres()));
        }
        return movies;
    }

    public String getGenresStr(Set<Genre> genres) {
        Genre[] genresArray = genres.toArray(new Genre[0]);
        StringBuilder str = new StringBuilder();
        for (Genre genre : genresArray) {
            str.append(genre.getName()).append(" ");
        }
        return str.toString();
    }

    public MovieDTO findById(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.map(movieMapper::toDto).orElse(null);
    }

    public List<MovieDTO> getWatchlistMovies(List<Long> ids) {
        return movieRepository.findAllIds(ids).stream().map(movieMapper::toDto).collect(Collectors.toList());
    }

    public MovieDTO create(MovieDTO movieDTO) {
        Movie movie = Movie.builder()
                .title(movieDTO.getTitle())
                .description(movieDTO.getDescription())
                .imageLink(movieDTO.getImageLink())
                .backdropLink(movieDTO.getBackdropLink())
                .genres(getGenres(movieDTO.getGenresString()))
                .rating(0)
                .build();
        return movieMapper.toDto(movieRepository.save(movie));
    }

    public MovieDTO edit(MovieDTO movieDTO) {
        Movie movie = Movie.builder()
                .id(movieDTO.getId())
                .title(movieDTO.getTitle())
                .description(movieDTO.getDescription())
                .imageLink(movieDTO.getImageLink())
                .backdropLink(movieDTO.getBackdropLink())
                .genres(getGenres(movieDTO.getGenresString()))
                .rating(movieDTO.getRating())
                .build();
        return movieMapper.toDto(movieRepository.save(movie));
    }

    public Set<Genre> getGenres(String genresString) {
        String[] split = genresString.split(" ");
        Set<Genre> genres = new HashSet<>();
        for(String s: split) {
            Optional<Genre> genre = genreRepository.findByName(EGenre.valueOf(s));
            genre.ifPresent(genres::add);
        }

        return genres;
    }

    public void delete(Long id) {
        movieRepository.deleteById(id);
    }
}
