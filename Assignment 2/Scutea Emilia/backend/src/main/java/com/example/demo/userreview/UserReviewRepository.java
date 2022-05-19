package com.example.demo.userreview;

import com.example.demo.userreview.model.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface UserReviewRepository extends JpaRepository<UserReview, Long> {
    ArrayList<UserReview> findAllByUserId(Long user_id);
}
