package com.app.review;

import com.app.review.model.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.app.UrlMapping.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(REVIEWS)
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping(ADD_REVIEW)
    public ReviewDTO save(@RequestBody ReviewDTO reviewDTO) {
        return reviewService.save(reviewDTO);
    }

    @GetMapping(GET_REVIEWS)
    public List<ReviewDTO> findByMovieId(@PathVariable Long id) {
        return reviewService.findByMovieId(id);
    }
}
