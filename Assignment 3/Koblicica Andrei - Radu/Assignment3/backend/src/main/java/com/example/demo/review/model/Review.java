package com.example.demo.review.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("reviews")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {

    @Id
    private String id;

    private Long userId;
    private String username;
    private Long movieId;
    private float rating;
    private String content;
}
