package com.example.demo.bookreview;

import com.example.demo.bookreview.model.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface BookReviewRepository extends JpaRepository<BookReview, Long> {
    List<BookReview> findAllByBookId(Long book_id);
}
