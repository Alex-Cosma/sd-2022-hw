package com.example.demo.review;

import com.example.demo.TestCreationFactory;
import com.example.demo.movie.MovieRepository;
import com.example.demo.movie.MovieService;
import com.example.demo.movie.dto.MovieDTO;
import com.example.demo.movie.model.Movie;
import com.example.demo.review.dto.ReviewDTO;
import com.example.demo.review.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.TestCreationFactory.*;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        reviewService=new ReviewService(reviewRepository, movieRepository, reviewMapper);
    }

    @Test
    void create(){
        Review review=newReview();
        ReviewDTO reviewDTO=newReviewDTO();
        Movie movie=newMovie();
        when(movieRepository.findById(reviewDTO.getMovieId())).thenReturn(of(movie));
        when(movieRepository.save(movie)).thenReturn(movie);
        when(reviewMapper.toDto(review)).thenReturn(reviewDTO);
        when(reviewMapper.fromDto(reviewDTO)).thenReturn(review);
        when(reviewRepository.save(review)).thenReturn(review);

        ReviewDTO newReviewDTO=reviewService.create(reviewDTO);
        assertEquals(reviewDTO,newReviewDTO);
    }

    @Test
    void findByMovieId(){
        Long id=randomLong();
        List<Review> reviews=new ArrayList<>();
        for(int i=0;i<5;i++){
            reviews.add(newReview());
        }
        List<ReviewDTO> reviewDTOS = reviews.stream().map(reviewMapper::toDto).collect(Collectors.toList());
        when(reviewRepository.findAllByMovieId(id)).thenReturn(reviews);
        for(int i=0;i<reviews.size();i++){
            when(reviewMapper.toDto(reviews.get(i))).thenReturn(reviewDTOS.get(i));
        }

        List<ReviewDTO> newReviewDTOS=reviewService.findByMovieId(id);
        assertEquals(reviews.size(),newReviewDTOS.size());
    }
}
