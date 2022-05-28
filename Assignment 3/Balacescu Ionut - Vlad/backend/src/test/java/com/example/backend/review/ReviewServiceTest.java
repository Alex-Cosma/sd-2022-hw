package com.example.backend.review;

import com.example.backend.TestCreationFactory;
import com.example.backend.game.GameRepository;
import com.example.backend.game.model.Game;
import com.example.backend.review.model.Review;
import com.example.backend.user.UserRepository;
import com.example.backend.user.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.backend.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Test
    void createReview() {
        User user = User.builder().username(randomString()).password(randomString()).email(randomEmail()).build();
        userRepository.save(user);
        Game game = newGame();
        gameRepository.save(game);
        Review review = Review.builder()
                .text(randomString())
                .rating((int) randomLong())
                .game(game)
                .user(user).build();
        when(reviewRepository.save(review)).thenReturn(review);

        Review newReview = reviewService.createReview(review);
        assertEquals(newReview.getUser().getUsername(), review.getUser().getUsername());
        assertEquals(newReview.getGame().getTitle(), review.getGame().getTitle());
    }

    @Test
    void getReviewsForGame() {
        long gameId = randomLong();
        List<Review> reviews = new ArrayList<>();
        User user = User.builder()
                .username(randomString())
                .password(UUID.randomUUID().toString())
                .email(randomEmail())
                .build();
        userRepository.save(user);
        Game game = newGame();
        gameRepository.save(game);
        Review review = Review.builder()
                .text(randomString())
                .rating((int) randomLong())
                .game(game)
                .user(user).build();
        reviewRepository.save(review);
        reviews.add(review);
        when(reviewRepository.findByGame_Id(gameId)).thenReturn(reviews);

        assertEquals(1,reviews.size());

    }
}