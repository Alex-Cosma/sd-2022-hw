package com.example.backend.review;

import com.example.backend.game.GameRepository;
import com.example.backend.game.model.Game;
import com.example.backend.review.model.Review;
import com.example.backend.user.UserRepository;
import com.example.backend.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.example.backend.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void findByGame_Id() {
        List<Review> reviews = new ArrayList<>();
        Game game = newGame();
        User user = User.builder()
                .password(randomString())
                .email(randomEmail())
                .username(randomString())
                .build();
        Review review = Review.builder()
                .user(user)
                .game(game)
                .rating((int)randomLong())
                .text(randomString())
                .build();
        gameRepository.save(game);
        userRepository.save(user);
        reviewRepository.save(review);
        reviews.add(review);

        List<Review> newReviews = reviewRepository.findByGame_Id(game.getId());
        assertEquals(newReviews.size(),reviews.size());
    }
}