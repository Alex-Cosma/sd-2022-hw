package com.example.demo.bookreview;

import com.example.demo.BaseControllerTest;
import com.example.demo.TestCreationFactory;
import com.example.demo.bookreview.model.Rating;
import com.example.demo.bookreview.model.dto.BookReviewDTO;
import com.example.demo.userreview.UserReviewService;
import com.example.demo.userreview.model.dto.UserReviewDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.demo.UrlMapping.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookReviewControllerTest extends BaseControllerTest {

    @InjectMocks
    private BookReviewController bookReviewController;

    @Mock
    private BookReviewService bookReviewService;

    @Mock
    private UserReviewService userReviewService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        bookReviewController = new BookReviewController(bookReviewService, userReviewService);
        mockMvc = MockMvcBuilders.standaloneSetup(bookReviewController).build();
    }

    @Test
    void getAllRatings() throws Exception {
        List<String> ratingList = Arrays.stream(Rating.values()).map(Objects::toString).collect(Collectors.toList());

        when(bookReviewService.getAllRatings()).thenReturn(ratingList);

        ResultActions response = performGet(REVIEWS + REVIEW_RATINGS);
        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(ratingList));
    }

    @Test
    void getReviews() throws Exception {
        List<BookReviewDTO> bookReviewDTOS = TestCreationFactory.listOf(BookReviewDTO.class);
        Long id = 1L;
        when(bookReviewService.getReviewsForBook(id)).thenReturn(bookReviewDTOS);

        ResultActions response = performGetWithPathVariables(REVIEWS + REVIEW_GET, id);
        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(bookReviewDTOS));
    }

    @Test
    void addReview() throws Exception{
        Long book_id = TestCreationFactory.randomLong();
        Long user_id = TestCreationFactory.randomLong();
        BookReviewDTO bookReviewDTO = TestCreationFactory.newBookReviewDTO();
        UserReviewDTO userReviewDTO = TestCreationFactory.newUserReviewDTO();

        when(bookReviewService.addReview(book_id, bookReviewDTO)).thenReturn(bookReviewDTO);

        when(userReviewService.convertReview(bookReviewDTO)).thenReturn(userReviewDTO);
        when(userReviewService.addReview(user_id, userReviewDTO)).thenReturn(userReviewDTO);

        ResultActions response = performPostWithRequestBodyAndPathVariables(REVIEWS + REVIEW_ADD_BOOK, bookReviewDTO, book_id, user_id);
        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(bookReviewDTO));

    }

}