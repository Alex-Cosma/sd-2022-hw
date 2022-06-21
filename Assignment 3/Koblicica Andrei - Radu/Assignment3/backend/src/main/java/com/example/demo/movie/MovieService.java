package com.example.demo.movie;

import com.example.demo.movie.dto.MovieDTO;
import com.example.demo.movie.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;



    private Movie findById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found: " + id));
    }

    public List<MovieDTO> findTopMovies(){
        Comparator<MovieDTO> compareByRating = Comparator.comparing(MovieDTO::getRating);
        return movieRepository.findAll().stream()
                .map(movieMapper::toDto)
                .sorted(compareByRating)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        l -> {
                            Collections.reverse(l); return l; }))
                .stream().limit(30).collect(Collectors.toList());
    }

    public List<MovieDTO> findAll() {
        return movieRepository.findAll().stream()
                .map(movieMapper::toDto)
                .collect(Collectors.toList());
    }
    public MovieDTO findMovie(Long id) {
        return movieMapper.toDto(findById(id));
    }

    public List<MovieDTO> findAllByFilter(String filter){
        return movieRepository.findAllByTitleLike("%"+filter+"%").stream()
                .map(movieMapper::toDto)
                .collect(Collectors.toList());
    }

    public MovieDTO create(MovieDTO item) {
        return movieMapper.toDto(movieRepository.save(
                movieMapper.fromDto(item)
        ));
    }

    public void delete(Long id){
        movieRepository.deleteById(id);
    }

    public MovieDTO update(Long id, MovieDTO movieDTO){
        Movie movie = findById(id);
        movie.setTitle(movieDTO.getTitle());
        movie.setDescription(movieDTO.getDescription());
        movie.setGenre(movieDTO.getGenre());
        movie.setImagePath(movieDTO.getImagePath());
        movie.setRating(movieDTO.getRating());
        movie.setYear(movieDTO.getYear());
        movie.setNumberOfReviews(movieDTO.getNumberOfReviews());
        return movieMapper.toDto(
                movieRepository.save(movie)
        );
    }





}
