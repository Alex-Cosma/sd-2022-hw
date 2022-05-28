package com.app.watchlist;

import com.app.watchlist.model.Watchlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WatchlistRepository extends MongoRepository<Watchlist, Long> {

    @Query(value="{ 'userId' : ?0, 'movieId' : ?1 }")
    Optional<Watchlist> findByUserIdAndMovieId(Long userId, Long movieId);

    @Query(value = "{ 'userId' : ?0 }")
    List<Watchlist> findByUserId(Long userId);
}
