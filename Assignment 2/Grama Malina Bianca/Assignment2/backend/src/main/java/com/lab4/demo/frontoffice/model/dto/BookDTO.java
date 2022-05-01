package com.lab4.demo.frontoffice.model.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private float price;
    private int quantity;
}
