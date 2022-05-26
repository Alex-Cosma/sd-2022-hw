package com.app.review.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Document("review")
public class Review {

    @Id
    private Long id;

    private Long movieId;

    private Long userId;

    private int rating;

    private String description;

    private String username;

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";
}
