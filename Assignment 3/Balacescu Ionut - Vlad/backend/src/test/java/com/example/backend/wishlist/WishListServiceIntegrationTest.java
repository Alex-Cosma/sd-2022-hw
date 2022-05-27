package com.example.backend.wishlist;

import com.example.backend.game.GameRepository;
import com.example.backend.game.model.Game;
import com.example.backend.user.UserRepository;
import com.example.backend.user.model.User;
import com.example.backend.wishlist.model.WishList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.backend.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WishListServiceIntegrationTest {

    @Autowired
    private WishListService wishListService;

    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;


    @Test
    void getWishListForUser() {
        User user = userRepository.save(User.builder()
                .username(randomString())
                .password(randomString())
                .email(randomEmail())
                .build());
        List<WishList> wishLists = new ArrayList<>();
        int games =10;
        for(int i=0;i<games;i++){
            Game game = newGame();
            gameRepository.save(game);
            WishList wishList = WishList.builder().user(user).game(game).build();
            wishListRepository.save(wishList);
            wishLists.add(wishList);
        }
        List<WishList> newWishLists = wishListRepository.findAllByUserId(user.getId());
        Assertions.assertEquals(games,newWishLists.size());
    }

    @Test
    void deleteGameFromWishList() {
        wishListRepository.deleteAll();
        User user = userRepository.save(User.builder()
                .username(randomString())
                .password(randomString())
                .email(randomEmail())
                .build());
        Game gameReq = new Game();
        int games =10;
        for(int i=0;i<games;i++){
            Game game = newGame();
            gameReq = gameRepository.save(game);
            WishList wishList = WishList.builder().user(user).game(game).build();
            wishListRepository.save(wishList);
        }
        wishListRepository.deleteByUserIdAndGameId(user.getId(),gameReq.getId());
        List<WishList> wishLists = wishListRepository.findAll();
        assertEquals(games-1,wishLists.size());
    }

    @Test
    void insertGameInWishlist() {
        wishListRepository.deleteAll();
        User user = userRepository.save(User.builder()
                .username(randomString())
                .password(randomString())
                .email(randomEmail())
                .build());
        Game game = newGame();
        gameRepository.save(game);
        WishList wishList = WishList.builder().game(game).user(user).build();
        wishListRepository.save(wishList);
        List<WishList> wishLists = wishListRepository.findAllByUserId(user.getId());
        System.out.println(wishLists.size());
        assertEquals(wishLists.size(),1);

    }
}