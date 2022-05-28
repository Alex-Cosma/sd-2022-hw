package com.lab4.demo.review;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.review.model.Review;
import com.lab4.demo.review.model.dto.ReviewDTO;
import com.lab4.demo.user.UserService;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private UserService userService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reviewService = new ReviewService(reviewRepository, reviewMapper,userService);
    }

    @Test
    void findAll() {
        List<Review> reviews = TestCreationFactory.listOf(Review.class);
        when(reviewRepository.findAll()).thenReturn(reviews);

        List<ReviewDTO> all = reviewService.findAll();

        assertEquals(reviews.size(), all.size());
    }

    @Test
    void findById(){
        List<Review> reviews = TestCreationFactory.listOf(Review.class);
        ReviewDTO r = ReviewDTO.builder()
                .id(reviews.get(0).getId())
                .text(reviews.get(0).getText())
                .rating(reviews.get(0).getRating())
                .reviewer(null)
                .build();

        when(reviewRepository.findById(reviews.get(0).getId())).thenReturn(Optional.of(reviews.get(0)));
        when(reviewMapper.toDto(reviews.get(0))).thenReturn(r);

        ReviewDTO r1 = reviewService.findById(reviews.get(0).getId());

        assertEquals(r1.getId(),reviews.get(0).getId());
    }

    @Test
    void filterReviews(){
        PageRequest pageRequest = PageRequest.of(0, 10);
        String filter = "a";
        Page<Review> reviews = new PageImpl(List.of(Review.builder().text("a").rating("a").build()),pageRequest,10);
        when(reviewRepository.findAllByRatingLikeOrTextLike("%" +filter + "%","%" + filter + "%",pageRequest)).thenReturn(reviews);

        List<ReviewDTO> all = reviewService.filterReviews(filter);

        assertEquals(all.size(), 1);
    }

    @Test
    void create(){
        User user = User.builder()
                .id(1L)
                .username("user12")
                .password("pass1")
                .email("user12@email.com")
                .rankingPoints(0)
                .build();
        Review review = Review.builder()
                .id(1L)
                .text("a")
                .rating("a")
                .user(user)
                .build();
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .id(1L)
                .text("a")
                .rating("a")
                .reviewer(user.getEmail())
                .build();
        when(reviewMapper.fromDto(reviewDTO)).thenReturn(review);
        when(userService.findByEmail(reviewDTO.getReviewer())).thenReturn(user);
        when(reviewMapper.toDto(reviewRepository.save(review))).thenReturn(reviewDTO);
        ReviewDTO createdReview = reviewService.create(reviewDTO);
        Assertions.assertEquals(createdReview.getText(),review.getText());
    }

    @Test
    void edit(){
       User user = User.builder()
               .id(1L)
               .username("user1")
               .password("pass1")
               .email("user1@email.com")
               .rankingPoints(0)
               .build();
        Review review = Review.builder()
                .id(1L)
                .text("a")
                .rating("a")
                .user(user)
                .build();
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .id(1L)
                .text("a")
                .rating("a")
                .reviewer(user.getEmail())
                .build();

        when(reviewRepository.findById(reviewDTO.getId())).thenReturn(Optional.of(review));
        when(reviewMapper.toDto(reviewRepository.save(review))).thenReturn(reviewDTO);
        when(reviewMapper.fromDto(reviewDTO)).thenReturn(review);
        when(userService.findByEmail(reviewDTO.getReviewer())).thenReturn(user);

        ReviewDTO createdReview = reviewService.create(reviewDTO);
        Assertions.assertEquals("a",createdReview.getText());
        createdReview.setText("NewTitle");
        ReviewDTO editedReview= reviewService.edit(createdReview.getId(),createdReview);
        Assertions.assertEquals("NewTitle" ,editedReview.getText());

    }

    @Test
    void delete(){
        Long id = 1L;
        Review review = Review.builder()
                .id(id)
                .text("a")
                .rating("a")
                .user(null)
                .build();

        when(reviewRepository.save(review)).thenReturn(review);
        when(reviewRepository.findById(id)).thenReturn(java.util.Optional.of(review));
        when(reviewRepository.existsById(id)).thenReturn(false);

        reviewService.delete(id);
        Assertions.assertFalse(reviewRepository.existsById(id));
    }
}
