package com.example.demo.bookreview;

import com.example.demo.TestCreationFactory;
import com.example.demo.book.BookRepository;
import com.example.demo.book.BookstoreService;
import com.example.demo.book.model.Book;
import com.example.demo.bookreview.model.BookReview;
import com.example.demo.bookreview.model.dto.BookReviewDTO;
import com.example.demo.user.UserRepository;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookReviewServiceIntegrationTest {

    @Autowired
    private BookReviewService bookReviewService;

    @Autowired
    private BookReviewRepository bookReviewRepository;

    @Autowired
    private BookstoreService bookstoreService;

    @Autowired
    private BookReviewMapper bookReviewMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

//    @BeforeEach
//    void setUp() {
//        bookReviewRepository.deleteAll();
//        bookRepository.deleteAll();
//        userRepository.deleteAll();
//    }

    @Test
    void getReviewsForItem() {
        bookReviewRepository.deleteAll();
        bookRepository.deleteAll();
        userRepository.deleteAll();
        Book book = TestCreationFactory.newBook();
        bookRepository.save(book);

        BookReview review = TestCreationFactory.newBookReview();

        review.setBook(book);

        bookReviewRepository.save(review);

        book = bookRepository.findAll().get(0);

        bookReviewService.addReview(book.getId(), bookReviewMapper.toDto(review));
        List<BookReviewDTO> bookReviewList = book.getReviews().stream().map(bookReviewMapper::toDto).collect(Collectors.toList());
        List<BookReviewDTO> bookReviewDTOS = bookReviewService.getReviewsForItem(book.getId());
        assertEquals(bookReviewList.size(),bookReviewDTOS.size() );
    }

    @Test
    void addReview() {
        bookReviewRepository.deleteAll();
        bookRepository.deleteAll();
        userRepository.deleteAll();

        Book book = TestCreationFactory.newBook();
        book = bookRepository.save(book);

        BookReview review = TestCreationFactory.newBookReview();

        bookReviewService.addReview(book.getId(),bookReviewMapper.toDto(review));

        List<BookReview> bookReviewList = bookstoreService.findById(book.getId()).getReviews();
        List<BookReview> bookReviewList1 = bookReviewService.getReviewsForItem(book.getId()).stream().map(bookReviewMapper::fromDto).collect(Collectors.toList());

        assertEquals(bookReviewList.size(), bookReviewList1.size());
        for(int index  = 0; index < bookReviewList.size(); index++){
            assertEquals(bookReviewList.get(index).getId(), bookReviewList1.get(index).getId());
            assertEquals(bookReviewList.get(index).getText(), bookReviewList1.get(index).getText());
            assertEquals(bookReviewList.get(index).getRating(), bookReviewList1.get(index).getRating());
        }
    }
}