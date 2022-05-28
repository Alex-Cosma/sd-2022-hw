package com.app.review;

import com.app.review.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, Long> {
    List<Review> findByMovieId(Long movieId);
}
