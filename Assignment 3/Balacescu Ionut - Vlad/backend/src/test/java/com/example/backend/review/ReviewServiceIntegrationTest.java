package com.example.backend.review;

import com.example.backend.game.GameRepository;
import com.example.backend.game.model.Game;
import com.example.backend.review.model.Review;
import com.example.backend.review.model.dto.ReviewDTO;
import com.example.backend.user.UserRepository;
import com.example.backend.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static com.example.backend.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewServiceIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    @AfterEach
    void cleanup() {
        reviewRepository.deleteAll();
        gameRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    void createReview() {
        Game game = newGame();
        User user = User.builder()
                .username(randomString())
                .password(randomString())
                .email(randomEmail()).build();
        Review review = Review.builder()
                .user(user)
                .game(game)
                .rating((int)randomLong())
                .text(randomString()).build();
        gameRepository.save(game);
        userRepository.save(user);
        Review newReview = reviewRepository.save(review);
        Assertions.assertEquals(review.getGame().getTitle(),newReview.getGame().getTitle());
        Assertions.assertEquals(review.getUser().getUsername(),newReview.getUser().getUsername());
    }

    @Test
    void getReviewsForGame() {
        User user = User.builder().username(randomString()).password(randomString()).email(randomEmail()).build();
        userRepository.save(user);
        Game game = newGame();
        game = gameRepository.save(game);

        Review reviewToBeAdded = Review.builder().game(game).user(user).text(randomString()).build();
        reviewRepository.save(reviewToBeAdded);

        List<ReviewDTO> reviewsForItem = reviewService.getReviewsForGame(game.getId());

        assertEquals(1, reviewsForItem.size());
    }
}