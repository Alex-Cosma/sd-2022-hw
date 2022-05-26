package com.app.review;

import com.app.TestCreationFactory;
import com.app.review.model.Review;
import com.app.review.model.dto.ReviewDTO;
import com.app.review.model.mapper.ReviewMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReviewServiceIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();
    }

    @Test
    void save() {
        ReviewDTO reviewDTO = TestCreationFactory.newReviewDTO();
        ReviewDTO savedReview = reviewService.save(reviewDTO);

        assertEquals(reviewDTO.getDescription(), savedReview.getDescription());
    }

    @Test
    void findByMovieId() {
        List<Review> reviews = TestCreationFactory.listOf(Review.class);

        reviews.get(0).setMovieId(1L);
        reviews.get(1).setMovieId(1L);

        reviewRepository.saveAll(reviews);

        List<ReviewDTO> reviewDTOS = reviewService.findByMovieId(1L);

        assertEquals(2, reviewDTOS.size());
    }
}
