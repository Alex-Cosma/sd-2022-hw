package com.example.demo.bookreview;

import com.example.demo.TestCreationFactory;
import com.example.demo.book.BookMapper;
import com.example.demo.book.BookRepository;
import com.example.demo.book.BookstoreService;
import com.example.demo.bookreview.model.BookReview;
import com.example.demo.bookreview.model.dto.BookReviewDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class BookReviewServiceTest {
    @Mock
    private BookReviewRepository bookReviewRepository;

    @Mock
    private BookReviewService bookReviewService;

    @Mock
    private BookReviewMapper bookReviewMapper;

    @Mock
    private BookstoreService bookstoreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookReviewService = new BookReviewService(bookReviewRepository, bookReviewMapper, bookstoreService);
    }

    @Test
    void getReviews() {
        List<BookReviewDTO> bookReview = TestCreationFactory.listOf(BookReviewDTO.class);
        Long id = 1L;
        when(bookReviewService.getReviewsForItem(id)).thenReturn(bookReview);
    }


    @Test
    void addReview() {
        BookReviewDTO bookReviewDTO = TestCreationFactory.newBookReviewDTO();
        Long id = 1L;
        when(bookReviewService.addReview(id, bookReviewDTO)).thenReturn(bookReviewDTO);

        BookReviewDTO bookReviewDTO1 = bookReviewService.addReview(id, bookReviewDTO);
        Assertions.assertEquals(bookReviewDTO.getText(), bookReviewDTO1.getText());
    }
}