package com.app.watchlist;

import com.app.TestCreationFactory;
import com.app.movie.MovieService;
import com.app.movie.model.Movie;
import com.app.movie.model.dto.MovieDTO;
import com.app.watchlist.model.Watchlist;
import com.app.watchlist.model.dto.WatchlistDTO;
import com.app.watchlist.model.mapper.WatchlistMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class WatchlistServiceTest {

    @InjectMocks
    private WatchlistService watchlistService;

    @Mock
    private WatchlistRepository watchlistRepository;

    @Mock
    private WatchlistSequenceGeneratorService watchlistSequenceGeneratorService;

    @Mock
    private WatchlistMapper watchlistMapper;

    @Mock
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        watchlistService = new WatchlistService(watchlistRepository, watchlistSequenceGeneratorService, watchlistMapper, movieService);
    }

    @Test
    void save() {
        Watchlist watchlist = TestCreationFactory.newWatchlist();
        WatchlistDTO watchlistDTO = TestCreationFactory.getWatchlistDTO(watchlist);
        when(watchlistMapper.toDto(watchlistRepository.save(watchlist))).thenReturn(watchlistDTO);

        WatchlistDTO savedWatchlist = watchlistService.save(watchlistDTO);

        assertEquals(watchlistDTO, savedWatchlist);
    }

    @Test
    void isInWatchlist() {
        Watchlist watchlist = TestCreationFactory.newWatchlist();
        long userId = watchlist.getUserId();
        long movieId = watchlist.getMovieId();

        when(watchlistRepository.findByUserIdAndMovieId(userId, movieId)).thenReturn(Optional.of(watchlist));

        assertTrue(watchlistService.isInWatchlist(userId, movieId));
    }

    @Test
    void findByUserId() {
        List<Watchlist> watchlists = TestCreationFactory.listOf(Watchlist.class);
        List<MovieDTO> movies = TestCreationFactory.listOf(MovieDTO.class);
        watchlists.get(0).setUserId(1L);
        Watchlist watchlist1 = watchlists.get(0);
        watchlist1.setMovieId(movies.get(0).getId());
        watchlists.get(1).setUserId(1L);
        Watchlist watchlist2 = watchlists.get(1);
        watchlist2.setMovieId(movies.get(1).getId());

        List<MovieDTO> expected = List.of(movies.get(0), movies.get(1));

        when(watchlistRepository.findByUserId(1L)).thenReturn(List.of(watchlist1, watchlist2));
        when(movieService.getWatchlistMovies(List.of(movies.get(0).getId(), movies.get(1).getId()))).thenReturn(expected);

        List<MovieDTO> actual = watchlistService.findByUserId(1L);

        assertEquals(expected, actual);
    }

    @Test
    void remove() {
        Watchlist watchlist = TestCreationFactory.newWatchlist();
        WatchlistDTO watchlistDTO = TestCreationFactory.getWatchlistDTO(watchlist);

        watchlistRepository.save(watchlist);

        when(watchlistRepository.findByUserIdAndMovieId(watchlistDTO.getUserId(), watchlistDTO.getMovieId()))
                .thenReturn(Optional.of(watchlist));

        watchlistService.remove(watchlistDTO);

    }
}
