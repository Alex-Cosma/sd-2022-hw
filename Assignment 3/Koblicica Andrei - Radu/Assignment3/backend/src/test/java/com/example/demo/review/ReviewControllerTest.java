package com.example.demo.review;

import com.example.demo.BaseControllerTest;
import com.example.demo.movie.MovieController;
import com.example.demo.movie.MovieService;
import com.example.demo.movie.dto.MovieDTO;
import com.example.demo.review.dto.ReviewDTO;
import com.example.demo.review.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.demo.TestCreationFactory.*;
import static com.example.demo.UrlMapping.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReviewControllerTest extends BaseControllerTest {

    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private ReviewService reviewService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        reviewController = new ReviewController(reviewService);
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    void reviewsByMovieId() throws Exception{
        Long movieId=randomLong();
        List<ReviewDTO> reviews = listOf(ReviewDTO.class);
        when(reviewService.findByMovieId(movieId)).thenReturn(reviews);

        ResultActions response = performGetWithPathVariable(MOVIES+REVIEWS, movieId);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reviews));
    }

    @Test
    void addReview() throws Exception{
        ReviewDTO reviewDTO=newReviewDTO();
        when(reviewService.create(reviewDTO)).thenReturn(reviewDTO);
        ResultActions response = performPostWithRequestBodyAndPathVariables(MOVIES+REVIEWS, reviewDTO,reviewDTO.getMovieId());

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reviewDTO));
    }
}
