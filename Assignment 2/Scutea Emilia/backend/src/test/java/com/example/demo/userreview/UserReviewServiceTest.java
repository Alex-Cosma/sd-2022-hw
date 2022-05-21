package com.example.demo.userreview;

import com.example.demo.TestCreationFactory;
import com.example.demo.book.BookstoreService;
import com.example.demo.bookreview.BookReviewService;
import com.example.demo.bookreview.model.dto.BookReviewDTO;
import com.example.demo.user.UserRepository;
import com.example.demo.user.UserService;
import com.example.demo.user.model.User;
import com.example.demo.userreview.model.UserReview;
import com.example.demo.userreview.model.dto.UserReviewDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserReviewServiceTest {

    @InjectMocks
    private UserReviewService userReviewService;

    @Mock
    private UserReviewRepository userReviewRepository;

    @Mock
    private UserReviewMapper userReviewMapper;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userReviewService = new UserReviewService(userService,userReviewRepository,userReviewMapper);
    }


    @Test
    void getReviewsForUser() {
        User user = TestCreationFactory.newUser();
        Long user_id = user.getId();

        List<UserReview> userReviewList = TestCreationFactory.listOf(UserReview.class);

        UserReview userReview = TestCreationFactory.newUserReview();
        UserReviewDTO userReviewDTO = TestCreationFactory.newUserReviewDTO();

        when(userReviewRepository.findAllByUserId(user_id)).thenReturn(userReviewList);
        when(userReviewMapper.toDto(userReview)).thenReturn(userReviewDTO);

        Assertions.assertEquals(userReviewList.size(), userReviewService.getReviewsForUser(user_id).size());
    }

    @Test
    void convertReview() {
        BookReviewDTO bookReviewDTO = TestCreationFactory.newBookReviewDTO();
        UserReviewDTO userReviewDTO = userReviewService.convertReview(bookReviewDTO);

        assertEquals(bookReviewDTO.getRating(), userReviewDTO.getRating());
        assertEquals(bookReviewDTO.getText(), userReviewDTO.getText());
    }

    @Test
    void addReview() {
        User user = TestCreationFactory.newUser();
        Long id  = user.getId();

        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user));

        UserReviewDTO userReviewDTO = TestCreationFactory.newUserReviewDTO();
        UserReview userReview = TestCreationFactory.newUserReview();

        when(userReviewMapper.fromDto(userReviewDTO)).thenReturn(userReview);
        when(userReviewRepository.save(userReview)).thenReturn(userReview);
        when(userReviewMapper.toDto(userReview)).thenReturn(userReviewDTO);

        Assertions.assertEquals(userReviewDTO.getText(), userReviewService.addReview(id, userReviewDTO).getText());

    }
}