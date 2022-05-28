package com.app.review;

import com.app.BaseControllerTest;
import com.app.TestCreationFactory;
import com.app.movie.MovieController;
import com.app.review.model.dto.ReviewDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.app.UrlMapping.*;
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
    void save() throws Exception {
        ReviewDTO reviewDTO = TestCreationFactory.newReviewDTO();
        when(reviewService.save(reviewDTO)).thenReturn(reviewDTO);

        ResultActions response = performPostWithRequestBody(REVIEWS + ADD_REVIEW, reviewDTO);

        response.andExpect(status().isOk()).andExpect(jsonContentToBe(reviewDTO));
    }

    @Test
    void findByMovieId() throws Exception {
        List<ReviewDTO> reviewDTOS = TestCreationFactory.listOf(ReviewDTO.class);
        for(ReviewDTO r: reviewDTOS) {
            r.setMovieId(1L);
        }
        when(reviewService.findByMovieId(1L)).thenReturn(reviewDTOS);

        ResultActions response = performGetWithPathVariable(REVIEWS + GET_REVIEWS, 1L);

        response.andExpect(status().isOk()).andExpect(jsonContentToBe(reviewDTOS));
    }
}
