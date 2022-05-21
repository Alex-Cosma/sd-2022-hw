package com.example.demo.bookreview;

import com.example.demo.bookreview.model.BookReview;
import com.example.demo.bookreview.model.dto.BookReviewDTO;
import com.example.demo.userreview.UserReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(REVIEWS)
@RequiredArgsConstructor
public class BookReviewController {
    private final BookReviewService bookReviewService;
    private final UserReviewService userReviewService;

    @GetMapping(REVIEW_GET)
    public List<BookReviewDTO> getReviews(@PathVariable Long id){
        return bookReviewService.getReviewsForBook(id);
    }

    @GetMapping(REVIEW_RATINGS)
    public List<String> getAllRatings(){
        return bookReviewService.getAllRatings();
    }

    @PostMapping(REVIEW_ADD_BOOK)
    public BookReviewDTO addReview(@PathVariable Long book_id, @PathVariable Long user_id, @RequestBody BookReviewDTO review){
        userReviewService.addReview(user_id, userReviewService.convertReview(review));
        return bookReviewService.addReview(book_id, review);
    }
}
