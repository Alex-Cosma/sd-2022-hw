package com.example.demo.movie;

import com.example.demo.movie.dto.MovieDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(MOVIES)
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;



    @CrossOrigin
    @GetMapping
    public List<MovieDTO> allMovies() {
        return movieService.findAll();
    }

    @CrossOrigin
    @GetMapping(MOVIE)
    public MovieDTO movieById(@PathVariable Long id) {
        return movieService.findMovie(id);
    }


    @CrossOrigin
    @GetMapping(FILTERED)
    public List<MovieDTO> filteredMovies(@PathVariable String filter) {
        return movieService.findAllByFilter(filter);
    }


    @CrossOrigin
    @PostMapping
    public MovieDTO create(@Valid @RequestBody MovieDTO movie) {
        return movieService.create(movie);
    }

    @CrossOrigin
    @PutMapping(MOVIES_ID_PART)
    public MovieDTO update(@PathVariable Long id, @Valid @RequestBody MovieDTO movie) {
        return movieService.update(id, movie);
    }

    @CrossOrigin
    @DeleteMapping(MOVIES_ID_PART)
    public void delete(@PathVariable Long id) {
        movieService.delete(id);
    }


}
