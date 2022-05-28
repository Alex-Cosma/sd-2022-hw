package com.app.movie.model.dto;

import com.app.movie.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private Long id;
    private String title;
    private String description;
    private String imageLink;
    private String backdropLink;
    private float rating;
    private Set<Genre> genres;
    private String genresString;
}
