package com.example.demo.bookreview;

import com.example.demo.TestCreationFactory;
import com.example.demo.bookreview.model.BookReview;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BookReviewRepositoryTest {

    @Autowired
    private BookReviewRepository bookReviewRepository;

    @BeforeEach
    public void setUp(){
        bookReviewRepository.deleteAll();
    }

    @Test
    void findAllByBookId() {
        List<BookReview> bookReviewList = TestCreationFactory.listOf(BookReview.class);
        bookReviewRepository.saveAll(bookReviewList);

        Assertions.assertTrue(bookReviewRepository.findAllByBookId(1L).isEmpty());
    }
}