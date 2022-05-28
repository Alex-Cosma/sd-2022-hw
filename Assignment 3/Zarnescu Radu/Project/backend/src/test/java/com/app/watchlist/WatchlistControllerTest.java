package com.app.watchlist;

import com.app.BaseControllerTest;
import com.app.TestCreationFactory;
import com.app.movie.MovieController;
import com.app.movie.model.dto.MovieDTO;
import com.app.watchlist.model.dto.WatchlistDTO;
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
    void save() throws Exception {
        WatchlistDTO watchlistDTO = TestCreationFactory.newWatchlistDTO();
        when(watchlistService.save(watchlistDTO)).thenReturn(watchlistDTO);

        ResultActions response = performPostWithRequestBody(WATCHLIST + ADD_WATCHLIST, watchlistDTO);

        response.andExpect(status().isOk()).andExpect(jsonContentToBe(watchlistDTO));
    }

    @Test
    void isInWatchlist() throws Exception {
        WatchlistDTO watchlistDTO = TestCreationFactory.newWatchlistDTO();
        when(watchlistService.isInWatchlist(watchlistDTO.getUserId(), watchlistDTO.getMovieId())).thenReturn(true);

        ResultActions response = performGetWithPathVariable(WATCHLIST + IS_IN_WATCHLIST, watchlistDTO.getUserId(), watchlistDTO.getMovieId());

        response.andExpect(status().isOk());
    }

    @Test
    void getMoviesInWatchlist() throws Exception {
        List<MovieDTO> movies = TestCreationFactory.listOf(MovieDTO.class);
        when(watchlistService.findByUserId(1L)).thenReturn(movies);

        ResultActions response = performGetWithPathVariable(WATCHLIST + GET_WATCHLIST, 1L);

        response.andExpect(status().isOk()).andExpect(jsonContentToBe(movies));
    }

    @Test
    void remove() throws Exception {
        WatchlistDTO watchlistDTO = TestCreationFactory.newWatchlistDTO();

        ResultActions response = performPostWithRequestBody(WATCHLIST + REMOVE_FROM_WATCHLIST, watchlistDTO);

        response.andExpect(status().isOk());
    }
}
