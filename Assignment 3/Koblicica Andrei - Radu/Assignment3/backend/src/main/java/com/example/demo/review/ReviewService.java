package com.example.demo.review;

import com.example.demo.movie.MovieRepository;
import com.example.demo.movie.dto.MovieDTO;
import com.example.demo.movie.model.Movie;
import com.example.demo.review.dto.ReviewDTO;
import com.example.demo.review.model.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final ReviewMapper reviewMapper;

    private Movie findMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found: " + id));
    }

    public List<ReviewDTO> findByUserId(Long id) {
        return reviewRepository.findAllByUserId(id).stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());

    }
    public List<ReviewDTO> findByMovieId(Long id) {
        return reviewRepository.findAllByMovieId(id).stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());

    }

    public ReviewDTO create(ReviewDTO reviewDTO) {
        Movie movie=findMovieById(reviewDTO.getMovieId());
        float newRating=(movie.getRating()*movie.getNumberOfReviews()
                +reviewDTO.getRating())
                /(movie.getNumberOfReviews()+1);
        movie.setRating(newRating);
        movie.setNumberOfReviews(movie.getNumberOfReviews()+1);
        movieRepository.save(movie);
        return reviewMapper.toDto(reviewRepository.save(
                reviewMapper.fromDto(reviewDTO)
        ));
    }


}
