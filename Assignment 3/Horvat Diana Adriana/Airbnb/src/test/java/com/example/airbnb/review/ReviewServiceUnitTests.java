package com.example.airbnb.review;

import com.example.airbnb.TestCreationFactory;
import com.example.airbnb.accommodation.AccommodationService;
import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.booking.BookingService;
import com.example.airbnb.review.model.Review;
import com.example.airbnb.review.model.dto.ReviewDTO;
import com.example.airbnb.user.UserService;
import com.example.airbnb.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class ReviewServiceUnitTests {
    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private UserService userService;

    @Mock
    private AccommodationService accommodationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reviewService = new ReviewService(reviewRepository, reviewMapper, userService, accommodationService);
    }

    @Test
    void create(){
//        User user = TestCreationFactory.user();
//        Accommodation accommodation = TestCreationFactory.accommodationWithUser(user);
//        ReviewDTO reviewDTO = TestCreationFactory.reviewDTO(user.getId(), accommodation.getId());
//        Review review = new Review();
//        review.setComments(reviewDTO.getContent());
//        review.setReviewer_name(user.getUsername());
//        review.setId(Integer.parseInt(reviewDTO.getId().toString()));
//
//        when(userService.findById(reviewDTO.getUserId())).thenReturn(user);
//        when(accommodationService.findById(reviewDTO.getAccommodationId())).thenReturn(accommodation);
//        when(reviewRepository.save(review)).thenReturn(review);
//        doNothing().when(accommodationService).save(accommodation);
//        when(reviewMapper.toDto(review)).thenReturn(reviewDTO);
//
//        ReviewDTO result = reviewService.create(reviewDTO);
//
//        assertEquals(reviewDTO, result);
    }

}