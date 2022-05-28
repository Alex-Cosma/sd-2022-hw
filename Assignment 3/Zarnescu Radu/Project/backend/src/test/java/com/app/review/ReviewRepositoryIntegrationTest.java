package com.app.review;

import com.app.TestCreationFactory;
import com.app.review.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReviewRepositoryIntegrationTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();
    }

    @Test
    void findByMovieId() {
        List<Review> someReviews = TestCreationFactory.listOf(Review.class);
        for(int i = 0; i < 3; i++) {
            someReviews.get(i).setMovieId(1L);
        }
        reviewRepository.saveAll(someReviews);

        List<Review> reviews = reviewRepository.findByMovieId(1L);

        assertEquals(3, reviews.size());
    }
}
