package com.example.demo.watchlist;

import com.example.demo.BaseControllerTest;
import com.example.demo.movie.dto.MovieDTO;
import com.example.demo.review.ReviewController;

import com.example.demo.review.dto.ReviewDTO;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.demo.TestCreationFactory.*;
import static com.example.demo.UrlMapping.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WatchlistControllerTest extends BaseControllerTest {


    @InjectMocks
    private WatchlistController watchlistController;

    @Mock
    private WatchlistService watchlistService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        watchlistController = new WatchlistController(watchlistService);
        mockMvc = MockMvcBuilders.standaloneSetup(watchlistController).build();
    }

    @Test
    void addToWatchlist() throws Exception{
       UserDTO user=newUserDTO();
       MovieDTO movie=newMovieDTO();
       when(watchlistService.addToWatchlist(user.getId(), movie.getId())).thenReturn(new ResponseEntity<>("Movie added to watchlist!", HttpStatus.OK));

        ResultActions response = performPostWithPathVariables(WATCHLIST+ADD_TO_WATCHLIST, user.getId(), movie.getId());

        response.andExpect(status().isOk())
                .andExpect(content().string("Movie added to watchlist!"));

    }

    @Test
    void removeFromWatchlist() throws Exception{
        UserDTO user=newUserDTO();
        MovieDTO movie=newMovieDTO();
        when(watchlistService.removeFromWatchlist(user.getId(), movie.getId())).thenReturn(new ResponseEntity<>("Movie removed from watchlist!", HttpStatus.OK));

        ResultActions response = performPostWithPathVariables(WATCHLIST+REMOVE_FROM_WATCHLIST, user.getId(), movie.getId());

        response.andExpect(status().isOk())
                .andExpect(content().string("Movie removed from watchlist!"));

    }

    @Test
    void getWatchlist() throws Exception{
        UserDTO user=newUserDTO();
        List<MovieDTO> movies=listOf(MovieDTO.class);
        when(watchlistService.getWatchlist(user.getId())).thenReturn(movies);

        ResultActions response = performGetWithPathVariable(WATCHLIST+GET_WATCHLIST, user.getId());

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(movies));
    }
}
