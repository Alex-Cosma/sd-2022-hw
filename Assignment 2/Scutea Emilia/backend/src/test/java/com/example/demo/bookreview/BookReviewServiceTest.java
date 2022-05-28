package com.example.demo.bookreview;

import com.example.demo.TestCreationFactory;
import com.example.demo.book.BookMapper;
import com.example.demo.book.BookRepository;
import com.example.demo.book.BookstoreService;
import com.example.demo.book.model.Book;
import com.example.demo.bookreview.model.BookReview;
import com.example.demo.bookreview.model.Rating;
import com.example.demo.bookreview.model.dto.BookReviewDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

class BookReviewServiceTest {

    @InjectMocks
    private BookReviewService bookReviewService;

    @Mock
    private BookReviewRepository bookReviewRepository;

    @Mock
    private BookReviewMapper bookReviewMapper;

    @Mock
    private BookstoreService bookstoreService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookReviewService = new BookReviewService(bookReviewRepository, bookReviewMapper, bookstoreService);
    }

    @Test
    void getReviews() {
        Book book = TestCreationFactory.newBook();
        Long book_id = book.getId();

        List<BookReview> bookReviewList = TestCreationFactory.listOf(BookReview.class);

        BookReview bookReview = TestCreationFactory.newBookReview();
        BookReviewDTO bookReviewDTO = TestCreationFactory.newBookReviewDTO();

        when(bookReviewRepository.findAllByBookId(book_id)).thenReturn(bookReviewList);

        when(bookReviewMapper.toDto(bookReview)).thenReturn(bookReviewDTO);


        Assertions.assertEquals(bookReviewList.size(), bookReviewService.getReviewsForBook(book_id).size());
    }


    @Test
    void addReview() {
        Book book = TestCreationFactory.newBook();

        Long id = book.getId();
        when(bookRepository.save(book)).thenReturn(book);
        when(bookRepository.findById(id)).thenReturn(Optional.ofNullable(book));

        BookReviewDTO bookReviewDTO = TestCreationFactory.newBookReviewDTO();
        BookReview bookReview = TestCreationFactory.newBookReview();

        when(bookReviewMapper.fromDto(bookReviewDTO)).thenReturn(bookReview);
        when(bookReviewRepository.save(bookReview)).thenReturn(bookReview);
        when(bookReviewMapper.toDto(bookReview)).thenReturn(bookReviewDTO);

        Assertions.assertEquals(bookReviewDTO.getText(), bookReviewService.addReview(id,bookReviewDTO).getText());
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