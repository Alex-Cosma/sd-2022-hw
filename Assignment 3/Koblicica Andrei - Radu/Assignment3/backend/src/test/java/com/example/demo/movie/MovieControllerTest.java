package com.example.demo.movie;

import com.example.demo.BaseControllerTest;
import com.example.demo.movie.dto.MovieDTO;
import com.example.demo.movie.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.demo.TestCreationFactory.*;
import static com.example.demo.UrlMapping.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MovieControllerTest extends BaseControllerTest {

    @InjectMocks
    private MovieController movieController;

    @Mock
    private MovieService movieService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        movieController = new MovieController(movieService);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
    void allMovies() throws Exception {
        List<MovieDTO> movies = listOf(Movie.class);
        when(movieService.findAll()).thenReturn(movies);

        ResultActions response = performGet(MOVIES);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(movies));

    }

    @Test
    void filteredMovies() throws Exception {
        String filter=randomString();
        List<MovieDTO> movies = listOf(MovieDTO.class);
        when(movieService.findAllByFilter(filter)).thenReturn(movies);

        ResultActions response = performGetWithPathVariable(MOVIES+FILTERED, filter);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(movies));

    }

    @Test
    void movieById() throws Exception {
        Long id=randomLong();
        MovieDTO movie =newMovieDTO();
        when(movieService.findMovie(id)).thenReturn(movie);

        ResultActions response = performGetWithPathVariable(MOVIES+MOVIE, id);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(movie));

    }



    @Test
    void create() throws Exception {
        MovieDTO movieDTO = newMovieDTO();

        when(movieService.create(movieDTO)).thenReturn(movieDTO);

        ResultActions result = performPostWithRequestBody(MOVIES, movieDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(movieDTO));
    }

    @Test
    void update() throws Exception {
        MovieDTO movieDTO=newMovieDTO();

        when(movieService.update(movieDTO.getId(), movieDTO)).thenReturn(movieDTO);

        ResultActions result = performPutWithRequestBodyAndPathVariables(MOVIES + MOVIES_ID_PART, movieDTO, movieDTO.getId());
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(movieDTO));
    }

    @Test
    void delete() throws Exception {
        MovieDTO movie=newMovieDTO();
        movieService.create(movie);
        ResultActions result = performDeleteWithPathVariables(MOVIES + MOVIES_ID_PART, movie.getId());
        result.andExpect(status().isOk());

    }


}
