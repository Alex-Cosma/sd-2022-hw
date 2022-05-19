package com.example.demo.bookreview;

import com.example.demo.bookreview.model.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Set;

public interface BookReviewRepository extends JpaRepository<BookReview, Long> {
    ArrayList<BookReview> findAllByBookId(Long book_id);
}
