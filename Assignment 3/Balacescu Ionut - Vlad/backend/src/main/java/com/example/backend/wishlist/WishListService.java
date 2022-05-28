package com.example.backend.wishlist;

import com.example.backend.game.model.Game;
import com.example.backend.user.dto.UserDTO;
import com.example.backend.user.mapper.UserMapper;
import com.example.backend.user.model.User;
import com.example.backend.wishlist.model.WishList;
import com.example.backend.wishlist.model.dto.WishListDTO;
import kong.unirest.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.example.backend.user.model.ERole.CUSTOMER;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListRepository wishListRepository;
    private final UserMapper userMapper;

    public List<WishList> getWishListForUser(Long userId){

        return wishListRepository.findAllByUserId(userId);
    }

    public void deleteGameFromWishList(Long userId, Long gameId) {
        wishListRepository.deleteByUserIdAndGameId(userId,gameId);
    }

    public WishList insertGameInWishlist(WishList wishList){

         return wishListRepository.save(wishList);
    }

}
