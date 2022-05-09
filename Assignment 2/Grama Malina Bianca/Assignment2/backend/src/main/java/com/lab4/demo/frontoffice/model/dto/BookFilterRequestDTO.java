package com.lab4.demo.frontoffice.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
public class BookFilterRequestDTO {
    @Builder.Default
    private final String name = "";
    @Builder.Default
    private final LocalDateTime createdAfter = null;
    @Builder.Default
    private final Boolean onlyOutOfStock = false;
    @Builder.Default
    private final Boolean withAdminReview = false;
}
