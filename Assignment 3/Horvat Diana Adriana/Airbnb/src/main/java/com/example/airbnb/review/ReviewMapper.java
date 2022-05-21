package com.example.airbnb.review;

import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.accommodation.model.dto.AccommodationDTO;
import com.example.airbnb.review.model.Review;
import com.example.airbnb.review.model.dto.ReviewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")

public interface ReviewMapper {
    @Mappings({
           @Mapping(target = "content", source = "comments"),
    })
    ReviewDTO toDto(Review review);

    Review fromDto(ReviewDTO reviewDTO);
}
