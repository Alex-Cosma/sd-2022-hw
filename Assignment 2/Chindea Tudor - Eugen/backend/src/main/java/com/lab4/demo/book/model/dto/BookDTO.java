package com.lab4.demo.book.model.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private Integer price;
    private Integer quantity;
}
