package com.example.demo.userreview;

import com.example.demo.TestCreationFactory;
import com.example.demo.book.BookstoreService;
import com.example.demo.bookreview.model.BookReview;
import com.example.demo.bookreview.model.dto.BookReviewDTO;
import com.example.demo.user.UserRepository;
import com.example.demo.user.UserService;
import com.example.demo.user.model.User;
import com.example.demo.userreview.model.UserReview;
import com.example.demo.userreview.model.dto.UserReviewDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserReviewServiceIntegrationTest {

    @Autowired
    private UserReviewService userReviewService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserReviewMapper userReviewMapper;

    @Autowired
    private UserReviewRepository userReviewRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userReviewRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getReviewsForUser() {
        User user = TestCreationFactory.newUser();
        userRepository.save(user);

        UserReview userReview = TestCreationFactory.newUserReview();
        userReview.setUser(user);

        userReviewRepository.save(userReview);

        user = userRepository.findAll().get(0);

        userReviewService.addReview(user.getId(), userReviewMapper.toDto(userReview));

        List<UserReviewDTO> userReviewDTOList = userReviewService.getReviewsForUser(user.getId());

        assertFalse(userReviewDTOList.isEmpty());
    }

    @Test
    void convertReview() {
        BookReviewDTO bookReview = TestCreationFactory.newBookReviewDTO();

        UserReviewDTO userReviewDTO = userReviewService.convertReview(bookReview);

        assertEquals(bookReview.getText(), userReviewDTO.getText());
        assertEquals(bookReview.getRating(), userReviewDTO.getRating());
    }

    @Test
    void addReview() {
        User user = TestCreationFactory.newUser();
        userRepository.save(user);

        UserReview userReview = TestCreationFactory.newUserReview();

        userReviewService.addReview(user.getId(), userReviewMapper.toDto(userReview));

        List<UserReview> userReviewList = userService.findById(user.getId()).getReviews();
        List<UserReview> userReviewList1 = userReviewService.getReviewsForUser(user.getId()).stream().map(userReviewMapper::fromDto).collect(Collectors.toList());

        assertFalse(userReviewList1.isEmpty());
        for(int index  = 0; index < userReviewList.size(); index++){
            assertEquals(userReviewList.get(index).getId(), userReviewList1.get(index).getId());
            assertEquals(userReviewList.get(index).getText(), userReviewList1.get(index).getText());
            assertEquals(userReviewList.get(index).getRating(), userReviewList1.get(index).getRating());
        }

    }
}