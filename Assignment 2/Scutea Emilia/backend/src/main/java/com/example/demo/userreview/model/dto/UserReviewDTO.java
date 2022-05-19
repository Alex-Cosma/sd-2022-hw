package com.example.demo.userreview.model.dto;

import com.example.demo.book.model.Book;
import com.example.demo.bookreview.model.Rating;
import com.example.demo.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserReviewDTO {
    private Long id;
    private String text;
    private String rating;
    private User user;
}
