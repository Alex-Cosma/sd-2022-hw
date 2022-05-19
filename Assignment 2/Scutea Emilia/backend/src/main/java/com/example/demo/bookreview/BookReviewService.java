package com.example.demo.bookreview;

import com.example.demo.book.BookstoreService;
import com.example.demo.book.model.Book;
import com.example.demo.bookreview.model.Rating;
import com.example.demo.bookreview.model.dto.BookReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookReviewService {
    private final BookReviewRepository bookReviewRepository;
    private final BookReviewMapper bookReviewMapper;
    private final BookstoreService bookstoreService;


    public List<String> getAllRatings(){
        return Arrays.stream(Rating.values()).map(Objects::toString).collect(Collectors.toList());
    }
    public List<BookReviewDTO> getReviewsForItem(Long itemId) {
        return bookReviewRepository.findAllByBookId(itemId)
                .stream()
                .map(bookReviewMapper::toDto)
                .collect(Collectors.toList());
    }


    public String getRating(String rating){
        for(Rating rating1: Rating.values()){
            if(rating1.name().equalsIgnoreCase(rating)){
                return rating1.name();
            }
        }
        return Rating.AVERAGE.name();
    }
    // add review to item
    public BookReviewDTO addReview(Long book_id, BookReviewDTO bookReview) {
            // get the book
            Book book = bookstoreService.findById(book_id);

            // set the book to the review
            bookReview.setBook(book);

            bookReview.setRating(getRating(bookReview.getRating()));

            // save the review
            return bookReviewMapper.toDto(bookReviewRepository.save(bookReviewMapper.fromDto(bookReview)));
    }
}
