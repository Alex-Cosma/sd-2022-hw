package com.example.backend.wishlist;

import com.example.backend.game.model.Game;
import com.example.backend.user.model.User;
import com.example.backend.wishlist.model.WishList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.backend.UrlMapping.*;

@RestController
@RequestMapping(WISHLIST)
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;

    @GetMapping
    public List<Game> getWishList(@PathVariable Long idUser){
        System.out.println(wishListService.getWishListForUser(idUser).size());
        return wishListService.getWishListForUser(idUser).stream()
                .map(WishList::getGame)
                .collect(Collectors.toList());
    }
    @DeleteMapping(WISHLIST_GAME)
    public void deleteFromWishList(@PathVariable Long idUser,@PathVariable Long idGame){
        wishListService.deleteGameFromWishList(idUser,idGame);
    }

}
