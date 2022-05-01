package com.lab4.demo.book.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class BookFilterRequestDTO {
    @Builder.Default
    private final String title = "";
    @Builder.Default
    private final String author = "";
    @Builder.Default
    private final String genre = "";
}
