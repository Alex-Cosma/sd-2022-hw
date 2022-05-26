package com.app.watchlist.model;

import com.app.movie.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document("watchlist_movie")
public class Watchlist {

    @Id
    private Long id;

    private Long userId;

    private Long movieId;

    @org.springframework.data.annotation.Transient
    public static final String SEQUENCE_NAME = "watchlists_sequence";

}
