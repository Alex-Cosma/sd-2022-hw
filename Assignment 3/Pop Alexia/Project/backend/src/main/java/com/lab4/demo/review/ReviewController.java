package com.lab4.demo.review;

import com.lab4.demo.review.model.dto.ReviewDTO;
import com.lab4.demo.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.lab4.demo.UrlMapping.REVIEW;

@RestController
@RequestMapping(REVIEW)
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public List<ReviewDTO> allReviews() {
        return reviewService.findAll();
    }


    @GetMapping("/filter/{filter}")
    public List<ReviewDTO> filterReviews(@PathVariable String filter){return reviewService.filterReviews(filter);}

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody ReviewDTO reviewDTO) {
        try {
            return ResponseEntity.ok().body(reviewService.create(reviewDTO));
        } catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getConstraintViolations().iterator().next().getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id , @RequestBody ReviewDTO reviewDTO) {
        try {
            reviewService.edit(id, reviewDTO);
            return ResponseEntity.ok(new MessageResponse("Review edited successfully"));
        } catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getConstraintViolations().iterator().next().getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if(reviewService.findById(id) == null)
            return ResponseEntity.badRequest().body(new MessageResponse("Review not found"));
        reviewService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Review deleted successfully"));
    }
}
