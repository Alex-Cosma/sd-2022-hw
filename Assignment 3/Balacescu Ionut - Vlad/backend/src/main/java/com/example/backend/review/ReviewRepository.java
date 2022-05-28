package com.example.backend.review;

import com.example.backend.review.model.Review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByGame_Id(Long gameId);
}
