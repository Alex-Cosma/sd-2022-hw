package com.example.demo.userreview;

import com.example.demo.userreview.model.UserReview;
import com.example.demo.userreview.model.dto.UserReviewDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserReviewMapper {
    UserReviewDTO toDto(UserReview review);

    UserReview fromDto(UserReviewDTO review);
}
