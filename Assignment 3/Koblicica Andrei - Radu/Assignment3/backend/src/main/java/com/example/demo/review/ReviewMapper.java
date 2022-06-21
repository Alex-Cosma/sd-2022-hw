package com.example.demo.review;

import com.example.demo.movie.dto.MovieDTO;
import com.example.demo.movie.model.Movie;
import com.example.demo.review.dto.ReviewDTO;
import com.example.demo.review.model.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewDTO toDto(Review review);
    Review fromDto(ReviewDTO reviewDTO);

}