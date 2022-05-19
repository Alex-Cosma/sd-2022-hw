package com.example.demo.userreview;

import com.example.demo.TestCreationFactory;
import com.example.demo.book.BookstoreService;
import com.example.demo.bookreview.BookReviewService;
import com.example.demo.bookreview.model.dto.BookReviewDTO;
import com.example.demo.user.UserService;
import com.example.demo.user.model.User;
import com.example.demo.userreview.model.dto.UserReviewDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserReviewServiceTest {

    @Mock
    private UserReviewRepository userReviewRepository;

    @InjectMocks
    private UserReviewService userReviewService;

    @Mock
    private UserReviewMapper userReviewMapper;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userReviewService = new UserReviewService(userService,userReviewRepository,userReviewMapper);
    }


    @Test
    void getReviewsForUser() {
        List<UserReviewDTO> userReviewDTOS = TestCreationFactory.listOf(UserReviewDTO.class);
        Long id = 1L;
        when(userReviewService.getReviewsForUser(id)).thenReturn(userReviewDTOS);
    }

    @Test
    void convertReview() {
        BookReviewDTO bookReviewDTO = TestCreationFactory.newBookReviewDTO();
        UserReviewDTO userReviewDTO = TestCreationFactory.newUserReviewDTO();
        when(userReviewService.convertReview(bookReviewDTO)).thenReturn(userReviewDTO);

        userReviewDTO = userReviewService.convertReview(bookReviewDTO);

        assertEquals(bookReviewDTO.getRating(), userReviewDTO.getRating());
        assertEquals(bookReviewDTO.getText(), userReviewDTO.getText());
    }

    @Test
    void addReview() {
        UserReviewDTO userReviewDTO = TestCreationFactory.newUserReviewDTO();
        Long id = 1L;
        when(userReviewService.addReview(id, userReviewDTO)).thenReturn(userReviewDTO);

        UserReviewDTO userReviewDTO1 = userReviewService.addReview(id, userReviewDTO);
        Assertions.assertEquals(userReviewDTO.getText(), userReviewDTO1.getText());

    }
}