package com.app.watchlist;

import com.app.TestCreationFactory;
import com.app.movie.MovieService;
import com.app.movie.model.Movie;
import com.app.movie.model.dto.MovieDTO;
import com.app.watchlist.model.Watchlist;
import com.app.watchlist.model.dto.WatchlistDTO;
import com.app.watchlist.model.mapper.WatchlistMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WatchlistServiceIntegrationTest {

    @Autowired
    WatchlistService watchlistService;

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private WatchlistSequenceGeneratorService watchlistSequenceGeneratorService;

    @Autowired
    private WatchlistMapper watchlistMapper;

    @Autowired
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        watchlistRepository.deleteAll();
    }

    @Test
    void save() {
        WatchlistDTO expected = TestCreationFactory.newWatchlistDTO();
        WatchlistDTO actual = watchlistService.save(expected);

        assertEquals(expected.getUserId(), actual.getUserId());
    }

    @Test
    void isInWatchlist() {
        Watchlist watchlist = TestCreationFactory.newWatchlist();
        watchlistRepository.save(watchlist);

        assertTrue(watchlistService.isInWatchlist(watchlist.getUserId(), watchlist.getMovieId()));
    }

    @Test
    void findByUserId() {
        MovieDTO movie1 = TestCreationFactory.newMovieDTO();
        MovieDTO movie2 = TestCreationFactory.newMovieDTO();

        MovieDTO movie1DTO = movieService.create(movie1);
        movieService.create(movie2);

        Watchlist watchlist = TestCreationFactory.newWatchlist();
        watchlist.setMovieId(movie1DTO.getId());

        watchlistRepository.save(watchlist);

        List<MovieDTO> actual = watchlistService.findByUserId(watchlist.getUserId());

        assertEquals(List.of(movie1DTO), actual);
    }

    @Test
    void remove() {
        Watchlist watchlist = TestCreationFactory.newWatchlist();
        watchlistRepository.save(watchlist);

        watchlistService.remove(watchlistMapper.toDto(watchlist));

        assertEquals(0, watchlistRepository.findAll().size());
    }
}
