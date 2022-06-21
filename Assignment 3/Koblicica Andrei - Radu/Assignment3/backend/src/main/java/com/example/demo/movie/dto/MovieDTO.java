package com.example.demo.movie.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO{
    private Long id;

    private String title;

    private String description;

    private String genre;

    private int year;

    @Min(0)
    private float rating;

    @Min(0)
    private int numberOfReviews;

    private String imagePath;


}
