package com.example.backend.wishlist;

import com.example.backend.TestCreationFactory;
import com.example.backend.game.GameRepository;
import com.example.backend.game.model.Game;
import com.example.backend.user.UserRepository;
import com.example.backend.user.model.User;
import com.example.backend.wishlist.model.WishList;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.backend.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class WishListServiceTest {
    @InjectMocks
    private WishListService wishListService;
    @Mock
    private WishListRepository wishListRepository;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private UserRepository userRepository;


    @Test
    void getWishListForUser() {
        long userId = randomLong();
        List<WishList> wishLists  = TestCreationFactory.listOf(WishList.class);
        when(wishListRepository.findAllByUserId(userId)).thenReturn(wishLists);
        wishListService.getWishListForUser(userId);


    }

    @Test
    void deleteGameFromWishList() {
    }

    @Test
    void insertGameInWishlist() {
        WishList wishList = newWishList();
        when(wishListRepository.save(wishList)).thenReturn(wishList);

        WishList newWishList = wishListService.insertGameInWishlist(wishList);
        assertEquals(wishList.getGame().getTitle(),newWishList.getGame().getTitle());
        assertEquals(wishList.getUser().getUsername(),newWishList.getUser().getUsername());

    }
}