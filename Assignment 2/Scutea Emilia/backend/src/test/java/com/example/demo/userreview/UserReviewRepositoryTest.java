package com.example.demo.userreview;

import com.example.demo.TestCreationFactory;
import com.example.demo.bookreview.model.BookReview;
import com.example.demo.userreview.model.UserReview;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserReviewRepositoryTest {

    @Autowired
    private UserReviewRepository userReviewRepository;

    @BeforeEach
    public void setUp(){
        userReviewRepository.deleteAll();
    }

    @Test
    void findAllByUserId() {
        List<UserReview> userReviewList = TestCreationFactory.listOf(UserReview.class);
        userReviewRepository.saveAll(userReviewList);

        Assertions.assertTrue(userReviewRepository.findAllByUserId(1L).isEmpty());

    }
}