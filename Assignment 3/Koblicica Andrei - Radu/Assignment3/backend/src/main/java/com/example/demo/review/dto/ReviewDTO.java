package com.example.demo.review.dto;


import lombok.*;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private String id;
    private Long userId;
    private String username;
    private Long movieId;
    private float rating;
    private String content;
}
