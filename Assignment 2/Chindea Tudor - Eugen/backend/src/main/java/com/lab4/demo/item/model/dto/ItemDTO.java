package com.lab4.demo.item.model.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ItemDTO {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private Integer price;
    private Integer quantity;
}
