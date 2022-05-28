package com.app.review.model.dto;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;
    private Long movieId;
    private Long userId;
    private int rating;
    private String username;
    private String description;
}
