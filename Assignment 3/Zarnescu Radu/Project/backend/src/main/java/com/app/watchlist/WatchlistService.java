package com.app.watchlist;

import com.app.movie.MovieService;
import com.app.movie.model.dto.MovieDTO;
import com.app.watchlist.model.Watchlist;
import com.app.watchlist.model.dto.WatchlistDTO;
import com.app.watchlist.model.mapper.WatchlistMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final WatchlistSequenceGeneratorService watchlistSequenceGeneratorService;
    private final WatchlistMapper watchlistMapper;
    private final MovieService movieService;

    public WatchlistDTO save(WatchlistDTO watchlistDTO) {
        Watchlist watchlist = Watchlist.builder()
                .id(watchlistSequenceGeneratorService.generateSequence(Watchlist.SEQUENCE_NAME))
                .userId(watchlistDTO.getUserId())
                .movieId(watchlistDTO.getMovieId())
                .build();

        return watchlistMapper.toDto(watchlistRepository.save(watchlist));
    }

    public boolean isInWatchlist(Long userId, Long movieId) {
        Optional<Watchlist> watchlist = watchlistRepository.findByUserIdAndMovieId(userId, movieId);
        return watchlist.isPresent();
    }

    public List<MovieDTO> findByUserId(Long userId) {

        List<Long> ids = watchlistRepository.findByUserId(userId).stream()
                .map(Watchlist::getMovieId)
                .collect(Collectors.toList());
        return movieService.getWatchlistMovies(ids);
    }

    public void remove(WatchlistDTO watchlistDTO) {
        Optional<Watchlist> watchlist = watchlistRepository.findByUserIdAndMovieId(watchlistDTO.getUserId(), watchlistDTO.getMovieId());
        watchlist.ifPresent(watchlistRepository::delete);
    }
}
