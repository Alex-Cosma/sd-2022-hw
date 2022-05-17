package com.lab4.demo.review;

import com.lab4.demo.review.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByRatingLikeOrTextLike(String s, String s1, Pageable pageRequest);
}
