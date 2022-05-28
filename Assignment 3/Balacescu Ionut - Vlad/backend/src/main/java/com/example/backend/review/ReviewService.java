package com.example.backend.review;

import com.example.backend.review.model.Review;
import com.example.backend.review.model.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public Review createReview(Review review ){

        return reviewRepository.save(review);
    }

    public List<ReviewDTO> getReviewsForGame(Long gameId){
        return reviewRepository.findByGame_Id(gameId).stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }
}
