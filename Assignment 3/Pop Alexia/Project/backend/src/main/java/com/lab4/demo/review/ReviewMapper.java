package com.lab4.demo.review;

import com.lab4.demo.review.model.Review;
import com.lab4.demo.review.model.dto.ReviewDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mappings({
            @Mapping(target = "reviewer", source = "review.user.email")
    })
    ReviewDTO toDto(Review review);

    @Mappings({
            @Mapping(target = "user.email", source = "reviewer")
    })
    Review fromDto(ReviewDTO reviewDTO);


}
