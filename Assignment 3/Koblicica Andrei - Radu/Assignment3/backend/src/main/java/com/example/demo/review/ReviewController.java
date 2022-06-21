package com.example.demo.review;

import com.example.demo.review.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(MOVIES)
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @CrossOrigin
    @GetMapping(REVIEWS)
    public List<ReviewDTO> reviewsByMovieId(@PathVariable Long id) {
        return reviewService.findByMovieId(id);
    }

    @CrossOrigin
    @PostMapping(REVIEWS)
    public ReviewDTO addReview(@PathVariable Long id,@RequestBody ReviewDTO review) {
        return reviewService.create(review);
    }
}
