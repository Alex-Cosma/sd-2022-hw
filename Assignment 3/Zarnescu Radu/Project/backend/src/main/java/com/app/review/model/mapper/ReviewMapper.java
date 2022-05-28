package com.app.review.model.mapper;

import com.app.review.model.Review;
import com.app.review.model.dto.ReviewDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewDTO toDto(Review review);

    Review fromDto(ReviewDTO reviewDTO);

}
