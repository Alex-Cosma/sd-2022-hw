package com.example.demo.review;

import com.example.demo.review.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review,Long> {

    @Query("{movieId:?0}")
    List<Review> findAllByMovieId(Long movieId);

    @Query("{userId:?0}")
    List<Review> findAllByUserId(Long userId);
}
