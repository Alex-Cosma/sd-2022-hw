package com.example.backend.wishlist;

import com.example.backend.BaseControllerTest;
import com.example.backend.TestCreationFactory;
import com.example.backend.game.GameController;
import com.example.backend.game.model.Game;
import com.example.backend.game.model.dto.GameDTO;
import com.example.backend.wishlist.model.WishList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.backend.TestCreationFactory.randomLong;
import static com.example.backend.UrlMapping.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class WishListControllerTest extends BaseControllerTest {
    @InjectMocks
    private WishListController wishListController;

    @Mock
    private WishListService wishListService;




    @BeforeEach
    protected void setUp() {
        super.setUp();
        wishListController = new WishListController(wishListService);
        mockMvc = MockMvcBuilders.standaloneSetup(wishListController).build();
    }

    @Test
    void getWishList() throws Exception {
        List<WishList> wishLists = TestCreationFactory.listOf(WishList.class);
        long userId = randomLong();
        when(wishListService.getWishListForUser(userId)).thenReturn(wishLists);

        ResultActions response = performGetWithPathVariable(WISHLIST,userId);

        response.andExpect(status().isOk());
    }

    @Test
    void deleteFromWishList() throws Exception {
        long userId = randomLong();
        long gameId = randomLong();

        doNothing().when(wishListService).deleteGameFromWishList(userId,gameId);
        ResultActions response = performDeleteWIth2PathVariable(WISHLIST + WISHLIST_GAME,userId,gameId);

        response.andExpect(status().isOk());


    }
}