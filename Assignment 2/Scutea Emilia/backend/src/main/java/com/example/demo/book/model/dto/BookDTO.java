package com.example.demo.book.model.dto;

import com.example.demo.bookreview.model.BookReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private Integer quantity;
    private Double price;
    private String description;
    private String imageUrl;
    private Integer pageCount;
    private List<BookReview> reviews;
}
