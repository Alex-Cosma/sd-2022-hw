package com.example.demo.movie.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 256, nullable = false)
    private String title;
    @Column(length = 2048, nullable = false)
    private String description;
    @Column(length = 256, nullable = false)
    private String genre;
    @Column(length = 256, nullable = false)
    private int year;
    @Column(nullable=false)
    @Min(0)
    private float rating;
    @Column(nullable=false)
    @Min(0)
    private int numberOfReviews;
    @Column(length=256)
    private String imagePath;


}
