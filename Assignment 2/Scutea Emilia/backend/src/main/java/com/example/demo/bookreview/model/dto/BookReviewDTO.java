package com.example.demo.bookreview.model.dto;

import com.example.demo.book.model.Book;
import com.example.demo.bookreview.model.Rating;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BookReviewDTO {
    private Long id;
    private String text;
    private String rating;
    private Book book;
}
