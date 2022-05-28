package com.example.backend.wishlist.model.dto;

import com.example.backend.game.model.Game;
import com.example.backend.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.UnknownServiceException;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishListDTO {
    private Long id;
    private Game game;
    private User user;
}
