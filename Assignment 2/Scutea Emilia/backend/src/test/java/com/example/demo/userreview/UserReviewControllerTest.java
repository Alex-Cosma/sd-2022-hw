package com.example.demo.userreview;

import com.example.demo.BaseControllerTest;
import com.example.demo.TestCreationFactory;
import com.example.demo.bookreview.BookReviewController;
import com.example.demo.userreview.model.dto.UserReviewDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.demo.UrlMapping.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserReviewControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserReviewController userReviewController;

    @Mock
    private UserReviewService userReviewService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        userReviewController = new UserReviewController(userReviewService);
        mockMvc = MockMvcBuilders.standaloneSetup(userReviewController).build();
    }

    @Test
    void getReviewsForUser() throws Exception {
        List<UserReviewDTO> userReviewDTOList = TestCreationFactory.listOf(UserReviewDTO.class);
        Long id = TestCreationFactory.randomLong();

        when(userReviewService.getReviewsForUser(id)).thenReturn(userReviewDTOList);

        ResultActions response = performGetWithPathVariables(REVIEWS_USERS + REVIEW_FOR_USER, id);
        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userReviewDTOList));
    }
}