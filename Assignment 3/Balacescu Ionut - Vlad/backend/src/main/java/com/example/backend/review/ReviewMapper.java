package com.example.backend.review;

import com.example.backend.review.model.Review;
import com.example.backend.review.model.dto.ReviewDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mappings({
            @Mapping(target = "reviewer", source = "review.user.email")
    })
    ReviewDTO toDto(Review review);
}
