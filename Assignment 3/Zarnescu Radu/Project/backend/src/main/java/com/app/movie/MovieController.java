package com.app.movie;

import com.app.movie.model.dto.MovieDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.app.UrlMapping.*;

@RestController
@RequestMapping(MOVIES)
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public List<MovieDTO> allMovies() {
        return movieService.allMovies();
    }

    @GetMapping(MOVIE_ID)
    public MovieDTO findById(@PathVariable Long id) {
        return movieService.findById(id);
    }

    @PostMapping(CREATE_MOVIE)
    public MovieDTO create(@RequestBody MovieDTO movieDTO) {
        return movieService.create(movieDTO);
    }

    @PutMapping(EDIT_MOVIE)
    public MovieDTO edit(@RequestBody MovieDTO movieDTO) {
        return movieService.edit(movieDTO);
    }

    @DeleteMapping(DELETE_MOVIE)
    public void delete(@PathVariable Long id) {
        movieService.delete(id);
    }

}
