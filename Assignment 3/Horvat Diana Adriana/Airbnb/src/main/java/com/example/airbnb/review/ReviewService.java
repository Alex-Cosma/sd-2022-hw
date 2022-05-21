package com.example.airbnb.review;

import com.example.airbnb.accommodation.AccommodationService;
import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.review.model.Review;
import com.example.airbnb.review.model.dto.ReviewDTO;
import com.example.airbnb.user.UserService;
import com.example.airbnb.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserService userService;
    private final AccommodationService accommodationService;

    public ReviewDTO create(ReviewDTO reviewDTO) {
        User user = userService.findById(reviewDTO.getUserId());
        Accommodation accommodation = accommodationService.findById(reviewDTO.getAccommodationId());
        Review review = new Review();
        review.setReviewer_name(user.getUsername());
        review.setComments(reviewDTO.getContent());
        Review review1 = reviewRepository.save(review);

        Set<Review> reviews = accommodation.getReviews();
        reviews.add(review);
        accommodationService.save(accommodation);
        accommodation.setReviews(reviews);
        return reviewMapper.toDto(review1);
    }
}
