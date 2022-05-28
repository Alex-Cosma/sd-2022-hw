package com.example.backend.game;


import com.example.backend.game.model.Game;
import com.example.backend.game.model.dto.GameDTO;
import com.example.backend.review.ReviewService;
import com.example.backend.review.model.Review;
import com.example.backend.review.model.dto.ReviewDTO;
import com.example.backend.user.model.ERole;
import com.example.backend.user.model.Role;
import com.example.backend.user.model.User;
import com.example.backend.wishlist.WishListService;
import com.example.backend.wishlist.model.WishList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.backend.UrlMapping.*;

@RestController
@RequestMapping(GAMES)
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    private final WishListService wishListService;

    private final ReviewService reviewService;

    @GetMapping
    public List<GameDTO> allGames(){
        return gameService.findAll();
    }

    @PostMapping
    public WishList addGame(@RequestBody WishList wishList){
        return wishListService.insertGameInWishlist(wishList);
    }

    @GetMapping(GAMES_ID_PART)
    public Game findById(@PathVariable Long id){
       return gameService.findById(id);
    }


    @PostMapping(GAMES_ID_PART)
    public Review addReview(@RequestBody Review review){
          return reviewService.createReview(review);
    }

    @GetMapping(REVIEWS)
    public List<ReviewDTO> getReviews(@PathVariable Long id){
        return reviewService.getReviewsForGame(id);
    }
}
