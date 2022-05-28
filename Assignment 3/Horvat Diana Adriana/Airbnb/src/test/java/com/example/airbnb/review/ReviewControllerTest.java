package com.example.airbnb.review;

import com.example.airbnb.TestCreationFactory;
import com.example.airbnb.booking.BookingController;
import com.example.airbnb.review.model.dto.ReviewDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.airbnb.user.UrlMapping.BOOKINGS;
import static com.example.airbnb.user.UrlMapping.REVIEWS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReviewControllerTest extends com.example.bookstore.BaseControllerTest {
    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private ReviewService reviewService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        reviewController = new ReviewController(reviewService);
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void create() throws Exception {
        ReviewDTO reviewDTO = TestCreationFactory.newReviewDTO();

        when(reviewService.create(reviewDTO)).thenReturn(reviewDTO);
        ResultActions result = performPostWithRequestBody(REVIEWS, reviewDTO);

        verify(reviewService, times(1)).create(reviewDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reviewDTO));
    }

}