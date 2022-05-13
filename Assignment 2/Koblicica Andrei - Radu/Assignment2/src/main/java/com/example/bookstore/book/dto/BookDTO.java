package com.example.bookstore.book.dto;

import com.example.bookstore.book.model.Book;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;

    private String title;

    private String author;

    private String genre;

    @Min(0)
    private float price;

    @Min(0)
    private int quantity;
}
