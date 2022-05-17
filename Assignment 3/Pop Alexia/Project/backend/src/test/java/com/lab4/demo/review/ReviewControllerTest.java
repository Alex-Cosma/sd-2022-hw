package com.lab4.demo.review;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.review.model.dto.ReviewDTO;
import com.lab4.demo.security.dto.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.UrlMapping.REVIEW;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReviewControllerTest extends BaseControllerTest {
    @InjectMocks
    private ReviewController controller;

    @Mock
    private ReviewService reviewService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new ReviewController(reviewService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allReviews() throws Exception {
        List<ReviewDTO> reviews = TestCreationFactory.listOf(ReviewDTO.class);
        when(reviewService.findAll()).thenReturn(reviews);

        ResultActions response = performGet(REVIEW);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reviews));
    }

    @Test
    void filterReviews() throws Exception {
        ReviewDTO reviewDTO1 = ReviewDTO.builder()
                .id(randomLong())
                .text("aa")
                .reviewer(null)
                .rating("aa")
                .build();
        ReviewDTO reviewDTO2 = ReviewDTO.builder()
                .id(randomLong())
                .text("ww")
                .reviewer(null)
                .rating("ww")
                .build();

        List<ReviewDTO> filteredReviews = List.of(reviewDTO2);

        when(reviewService.create(reviewDTO1)).thenReturn(reviewDTO1);
        when(reviewService.create(reviewDTO2)).thenReturn(reviewDTO2);
        when(reviewService.filterReviews("%aa%")).thenReturn(filteredReviews);

        ResultActions result1 = performPostWithRequestBody(REVIEW, reviewDTO1);
        result1.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reviewDTO1));

        ResultActions result2 = performPostWithRequestBody(REVIEW, reviewDTO2);
        result2.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reviewDTO2));

        ResultActions result3 = performGetWithPathVariable(REVIEW+"/filter/{filter}","%aa%" );
        result3.andExpect(status().isOk()).andExpect(jsonContentToBe(filteredReviews));
    }


    @Test
    void create() throws Exception {
        ReviewDTO reqReview = ReviewDTO.builder()
                .id(1L)
                .text("text")
                .rating("GOOD")
                .reviewer(null)
                .build();

        when(reviewService.create(reqReview)).thenReturn(reqReview);

        ResultActions result = performPostWithRequestBody(REVIEW, reqReview);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqReview));;
    }

    @Test
    void edit() throws Exception {
        ReviewDTO reqReview = ReviewDTO.builder()
                .id(randomLong())
                .text("text")
                .rating("GOOD")
                .reviewer(null)
                .build();

        when(reviewService.create(reqReview)).thenReturn(reqReview);
        ResultActions result = performPostWithRequestBody(REVIEW, reqReview);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqReview));

        reqReview.setText("anotherTitle");
        when(reviewService.edit(reqReview.getId(),reqReview)).thenReturn(reqReview);
        when(reviewService.findById(reqReview.getId())).thenReturn(reqReview);

        ResultActions result2 = performPutWithRequestBodyAndPathVariables(REVIEW+"/{id}", reqReview,reqReview.getId());
        result2.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Review edited successfully")));
    }

    @Test
    void delete() throws Exception {
        ReviewDTO reqReview = ReviewDTO.builder()
                .id(randomLong())
                .text("text")
                .rating("GOOD")
                .reviewer(null)
                .build();

        when(reviewService.create(reqReview)).thenReturn(reqReview);
        ResultActions result = performPostWithRequestBody(REVIEW, reqReview);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqReview));

        doNothing().when(reviewService).delete(reqReview.getId());

        ResultActions result2 = performDeleteWithPathVariable(REVIEW +"/{id}", reqReview.getId());

        result2.andExpect(status().isOk()).andExpect(jsonContentToBe(new MessageResponse("Review deleted successfully")));
        verify(reviewService, times(1)).delete(reqReview.getId());
    }

}
