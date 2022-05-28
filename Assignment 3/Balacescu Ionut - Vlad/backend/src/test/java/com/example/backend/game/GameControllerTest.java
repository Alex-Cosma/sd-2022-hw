package com.example.backend.game;
//unit test MOCK

import com.example.backend.BaseControllerTest;
import com.example.backend.TestCreationFactory;
import com.example.backend.game.model.Game;
import com.example.backend.game.model.dto.GameDTO;
import com.example.backend.review.ReviewService;
import com.example.backend.review.model.Review;
import com.example.backend.review.model.dto.ReviewDTO;
import com.example.backend.user.dto.UserDTO;
import com.example.backend.user.model.User;
import com.example.backend.wishlist.WishListService;
import com.example.backend.wishlist.model.WishList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.backend.TestCreationFactory.*;
import static com.example.backend.UrlMapping.*;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GameControllerTest extends BaseControllerTest {

    @InjectMocks
    private GameController gameController;

    @Mock
    private GameService gameService;

    @Mock
    private WishListService wishListService;

    @Mock
    private ReviewService reviewService;


    @BeforeEach
    protected void setUp() {
        super.setUp();
        gameController = new GameController(gameService, wishListService, reviewService);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
    }

    @Test
    void allGames() throws Exception {
        List<GameDTO> games = TestCreationFactory.listOf(Game.class);
        when(gameService.findAll()).thenReturn(games);

        ResultActions response = mockMvc.perform(get(GAMES));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(games));
    }

    @Test
    void addGame() throws Exception{
        WishList wishListTest = newWishList();
        when(wishListService.insertGameInWishlist(wishListTest)).thenReturn(wishListTest);

        ResultActions response = performPostWithRequestBody(GAMES,wishListTest);

        response.andExpect(status().isOk());

    }

    @Test
    void findById() throws Exception {
        long gameId = randomLong();
        Game game = newGame();
        when(gameService.findById(gameId)).thenReturn(game);

        ResultActions result = performGetWithPathVariable(GAMES + GAMES_ID_PART, gameId);

        result.andExpect(status().isOk()).andExpect(jsonContentToBe(game));

    }

    @Test
    void addReview() throws Exception {
        Game game = newGame();
        User newUser = User.builder().username(randomString())
                .email(randomEmail())
                .password(randomString()).build();
        Review review = Review.builder()
                .text(randomString())
                .rating((int)randomLong())
                .game(game)
                .user(newUser)
                .build();
        when(reviewService.createReview(review)).thenReturn(review);
        ResultActions response = performPostWithRequestBodyAndPathVariable(GAMES+GAMES_ID_PART,review,review.getGame().getId());
        response.andExpect(status().isOk());

    }

    @Test
    void getReviews() throws Exception {
        long id = randomLong();
        List<ReviewDTO> reviewDTOS = new ArrayList<>(listOf(ReviewDTO.class));
        when(reviewService.getReviewsForGame(id)).thenReturn(reviewDTOS);

        ResultActions result = performGetWithPathVariable(GAMES + REVIEWS, id );

        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reviewDTOS));
    }
}