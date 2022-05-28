package com.app.review;

import com.app.TestCreationFactory;
import com.app.review.model.Review;
import com.app.review.model.dto.ReviewDTO;
import com.app.review.model.mapper.ReviewMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reviewService = new ReviewService(reviewRepository, reviewMapper, sequenceGeneratorService);
    }

    @Test
    void save() {
        Review review = TestCreationFactory.newReview();
        ReviewDTO reviewDTO = TestCreationFactory.getReviewDTO(review);

        when(reviewMapper.toDto(reviewRepository.save(review))).thenReturn(reviewDTO);
        ReviewDTO savedReview = reviewService.save(reviewDTO);

        assertEquals(review.getDescription(), savedReview.getDescription());
    }

    @Test
    void findByMovieId() {
        List<Review> reviews = TestCreationFactory.listOf(Review.class);
        List<Review> someReviews = new ArrayList<>();
        Review review1 = reviews.get(0);
        review1.setMovieId(1L);
        Review review2 = reviews.get(1);
        review2.setMovieId(1L);
        someReviews.add(review1);
        someReviews.add(review2);

        List<ReviewDTO> reviewDTOS = someReviews.stream().map(TestCreationFactory::getReviewDTO).collect(Collectors.toList());

        when(reviewRepository.findByMovieId(1L)).thenReturn(someReviews);

        for(int i = 0; i < someReviews.size(); i++) {
            when(reviewMapper.toDto(someReviews.get(i))).thenReturn(reviewDTOS.get(i));
        }

        List<ReviewDTO> found = reviewService.findByMovieId(1L);

        assertEquals(2, found.size());
    }
}
