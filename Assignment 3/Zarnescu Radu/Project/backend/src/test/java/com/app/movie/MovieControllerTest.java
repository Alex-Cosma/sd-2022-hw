package com.app.movie;

import com.app.BaseControllerTest;
import com.app.TestCreationFactory;
import com.app.movie.model.dto.MovieDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.app.UrlMapping.*;
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
        List<MovieDTO> movies = TestCreationFactory.listOf(MovieDTO.class);
        when(movieService.allMovies()).thenReturn(movies);

        ResultActions response = performGet(MOVIES);

        response.andExpect(status().isOk()).andExpect(jsonContentToBe(movies));
    }

    @Test
    void findById() throws Exception {
        MovieDTO movieDTO = TestCreationFactory.newMovieDTO();
        when(movieService.findById(movieDTO.getId())).thenReturn(movieDTO);

        ResultActions response = performGetWithPathVariable(MOVIES + MOVIE_ID, movieDTO.getId());

        response.andExpect(status().isOk()).andExpect(jsonContentToBe(movieDTO));
    }

    @Test
    void create() throws Exception {
        MovieDTO movieDTO = TestCreationFactory.newMovieDTO();
        when(movieService.create(movieDTO)).thenReturn(movieDTO);

        ResultActions response = performPostWithRequestBody(MOVIES + CREATE_MOVIE, movieDTO);

        response.andExpect(status().isOk()).andExpect(jsonContentToBe(movieDTO));
    }

    @Test
    void edit() throws Exception {
        MovieDTO movieDTO = TestCreationFactory.newMovieDTO();
        when(movieService.edit(movieDTO)).thenReturn(movieDTO);

        ResultActions response = performPutWithRequestBody(MOVIES + EDIT_MOVIE, movieDTO);

        response.andExpect(status().isOk()).andExpect(jsonContentToBe(movieDTO));
    }

    @Test
    void delete() throws Exception {
        MovieDTO movieDTO = TestCreationFactory.newMovieDTO();

        ResultActions response = performDeleteWithPathVariable(MOVIES + DELETE_MOVIE, movieDTO.getId());

        response.andExpect(status().isOk());
    }
}
