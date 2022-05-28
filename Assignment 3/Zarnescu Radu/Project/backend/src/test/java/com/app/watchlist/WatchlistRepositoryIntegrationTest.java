package com.app.watchlist;

import com.app.TestCreationFactory;
import com.app.watchlist.model.Watchlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class WatchlistRepositoryIntegrationTest {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @BeforeEach
    void setUp() {
        watchlistRepository.deleteAll();
    }

    @Test
    void findByUserIdAndMovieId() {
        Watchlist watchlist = TestCreationFactory.newWatchlist();
        watchlistRepository.save(watchlist);

        Optional<Watchlist> actual = watchlistRepository.findByUserIdAndMovieId(watchlist.getUserId(), watchlist.getMovieId());

        assertTrue(actual.isPresent());
    }

    @Test
    void findByUserId() {
        Watchlist watchlist = TestCreationFactory.newWatchlist();
        watchlistRepository.save(watchlist);

        List<Watchlist> actual = watchlistRepository.findByUserId(watchlist.getUserId());

        assertEquals(1, actual.size());
    }
}
