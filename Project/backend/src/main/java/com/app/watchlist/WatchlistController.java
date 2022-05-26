package com.app.watchlist;

import com.app.movie.model.dto.MovieDTO;
import com.app.watchlist.model.dto.WatchlistDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.app.UrlMapping.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(WATCHLIST)
public class WatchlistController {

    private final WatchlistService watchlistService;

    @PostMapping(ADD_WATCHLIST)
    public WatchlistDTO save(@RequestBody WatchlistDTO watchlistDTO) {
        return watchlistService.save(watchlistDTO);
    }

    @GetMapping(IS_IN_WATCHLIST)
    public boolean isInWatchlist(@PathVariable Long userId, @PathVariable Long movieId){
        return watchlistService.isInWatchlist(userId, movieId);
    }

    @GetMapping(GET_WATCHLIST)
    public List<MovieDTO> getMoviesInWatchlist(@PathVariable Long userId){
        return watchlistService.findByUserId(userId);
    }

    @PostMapping(REMOVE_FROM_WATCHLIST)
    public void remove(@RequestBody WatchlistDTO watchlistDTO) {
        watchlistService.remove(watchlistDTO);
    }
}
