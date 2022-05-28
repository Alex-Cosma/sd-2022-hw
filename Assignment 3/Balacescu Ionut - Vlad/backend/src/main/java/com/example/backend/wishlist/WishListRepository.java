package com.example.backend.wishlist;

import com.example.backend.game.model.Game;
import com.example.backend.wishlist.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface WishListRepository extends JpaRepository<WishList,Long> {
      /*  List<WishList> findAllByUserId(Long userId);

        void deleteByUserIdAndGame_Id(Long userId, Long gameId);*/

    List<WishList> findAllByUserId(Long usedId);

    @Transactional
    void deleteByUserIdAndGameId(Long userId, Long gameId);
}
