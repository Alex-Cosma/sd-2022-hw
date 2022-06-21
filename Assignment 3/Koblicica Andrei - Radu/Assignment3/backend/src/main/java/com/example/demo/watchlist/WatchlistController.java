package com.example.demo.watchlist;

import com.example.demo.movie.dto.MovieDTO;
import com.example.demo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.UrlMapping.*;
import static com.example.demo.UrlMapping.REMOVE_FROM_WATCHLIST;

@RestController
@RequestMapping(WATCHLIST)
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;


    @CrossOrigin
    @PostMapping(ADD_TO_WATCHLIST)
    public ResponseEntity<String> addToWatchlist(@PathVariable Long userId, @PathVariable Long movieId){
        return watchlistService.addToWatchlist(userId, movieId);
    }

    @CrossOrigin
    @PostMapping(REMOVE_FROM_WATCHLIST)
    public ResponseEntity<String> removeFromWatchlist(@PathVariable Long userId, @PathVariable Long movieId){
        return watchlistService.removeFromWatchlist(userId, movieId);
    }

    @CrossOrigin
    @GetMapping(GET_WATCHLIST)
    public List<MovieDTO> getWatchlist(@PathVariable Long userId){
        return watchlistService.getWatchlist(userId);
    }
}
