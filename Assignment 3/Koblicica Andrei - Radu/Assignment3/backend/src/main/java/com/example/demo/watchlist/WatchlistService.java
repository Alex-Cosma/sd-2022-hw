package com.example.demo.watchlist;

import com.example.demo.movie.MovieMapper;
import com.example.demo.movie.MovieRepository;
import com.example.demo.movie.dto.MovieDTO;
import com.example.demo.movie.model.Movie;
import com.example.demo.user.UserRepository;
import com.example.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WatchlistService {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;


    public boolean isInWatchlist(User user,Movie movie){
        return user.getWatchlist().stream().anyMatch(m -> m.getId().equals(movie.getId()));
    }

    public ResponseEntity<String> addToWatchlist(Long userId, Long movieId){

        Movie movie=movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException("Movie not found: " + movieId));
        User user=userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        List<Movie> watchlistMovies=user.getWatchlist();
        if(!isInWatchlist(user,movie)){
            user.getWatchlist().add(movie);
            userRepository.save(user);
            return new ResponseEntity<>("Movie added to watchlist!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Movie already in watchlist!", HttpStatus.OK);
    }

    public ResponseEntity<String> removeFromWatchlist(Long userId, Long movieId){
        Movie movie=movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException("Movie not found: " + movieId));
        User user=userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        if(isInWatchlist(user,movie)){
            user.getWatchlist().remove(movie);
            userRepository.save(user);
            return new ResponseEntity<>("Movie removed from watchlist!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Movie isn't in watchlist!", HttpStatus.OK);
    }

    public List<MovieDTO> getWatchlist(Long userId) {
        User user=userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        return user.getWatchlist().stream()
                .map(movieMapper::toDto)
                .collect(Collectors.toList());
    }
}
