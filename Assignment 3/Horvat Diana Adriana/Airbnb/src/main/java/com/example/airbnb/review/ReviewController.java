package com.example.airbnb.review;

import com.example.airbnb.review.model.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.airbnb.user.UrlMapping.REVIEWS;

@RestController
@RequestMapping(REVIEWS)
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @CrossOrigin
    @PostMapping()
    public ReviewDTO create(@RequestBody ReviewDTO reviewDTO) {
        return reviewService.create(reviewDTO);
    }

}
