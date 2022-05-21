package com.example.demo.bookreview;

import com.example.demo.TestCreationFactory;
import com.example.demo.book.BookRepository;
import com.example.demo.book.BookstoreService;
import com.example.demo.book.model.Book;
import com.example.demo.bookreview.model.BookReview;
import com.example.demo.bookreview.model.Rating;
import com.example.demo.bookreview.model.dto.BookReviewDTO;
import com.example.demo.user.UserRepository;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    @BeforeEach
    void setUp() {
        bookReviewRepository.deleteAll();
        bookRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getReviewsForBook() {
//        bookReviewRepository.deleteAll();
//        bookRepository.deleteAll();
//        userRepository.deleteAll();
        Book book = TestCreationFactory.newBook();
        book = bookRepository.save(book);

        Long id = book.getId();

        BookReview review = TestCreationFactory.newBookReview();

        review.setBook(book);

        bookReviewRepository.save(review);

        book = bookRepository.findById(id).get();

        bookReviewService.addReview(book.getId(), bookReviewMapper.toDto(review));
        List<BookReviewDTO> bookReviewList = book.getReviews().stream().map(bookReviewMapper::toDto).collect(Collectors.toList());
        List<BookReviewDTO> bookReviewDTOS = bookReviewService.getReviewsForBook(book.getId());
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
        List<BookReview> bookReviewList1 = bookReviewService.getReviewsForBook(book.getId()).stream().map(bookReviewMapper::fromDto).collect(Collectors.toList());

        assertEquals(bookReviewList.size(), bookReviewList1.size());
        for(int index  = 0; index < bookReviewList.size(); index++){
            assertEquals(bookReviewList.get(index).getId(), bookReviewList1.get(index).getId());
            assertEquals(bookReviewList.get(index).getText(), bookReviewList1.get(index).getText());
            assertEquals(bookReviewList.get(index).getRating(), bookReviewList1.get(index).getRating());
        }
    }

    @Test
    void getAllRatings(){
        List<String> allRatings = Arrays.stream(Rating.values()).map(Objects::toString).collect(Collectors.toList());
        Assertions.assertEquals(allRatings.size(), bookReviewService.getAllRatings().size());
    }

    @Test
    void getRating(){
        String rating = "average";
        Assertions.assertEquals(Rating.AVERAGE.name(), bookReviewService.getRating(rating));
    }
}