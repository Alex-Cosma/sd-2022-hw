package com.example.assignment2.bookstore.model.dto;

import com.example.assignment2.bookstore.model.Book;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private int quantity;
    private int price;

}
