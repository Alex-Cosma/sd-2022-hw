package com.app.review;

import com.app.review.model.Review;
import com.app.review.model.dto.ReviewDTO;
import com.app.review.model.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final SequenceGeneratorService sequenceGeneratorService;

    public ReviewDTO save(ReviewDTO reviewDTO) {
        Review review = Review.builder()
                .id(sequenceGeneratorService.generateSequence(Review.SEQUENCE_NAME))
                .movieId(reviewDTO.getMovieId())
                .rating(reviewDTO.getRating())
                .userId(reviewDTO.getUserId())
                .description(reviewDTO.getDescription())
                .username(reviewDTO.getUsername())
                .build();

        return reviewMapper.toDto(reviewRepository.save(review));
    }

    public List<ReviewDTO> findByMovieId(Long id) {
        return reviewRepository.findByMovieId(id).stream().map(reviewMapper::toDto).collect(Collectors.toList());
    }
}
