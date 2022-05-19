package com.example.demo.bookreview;

import com.example.demo.bookreview.model.BookReview;
import com.example.demo.bookreview.model.dto.BookReviewDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookReviewMapper {
    BookReviewDTO toDto(BookReview review);

    BookReview fromDto(BookReviewDTO review);
}
