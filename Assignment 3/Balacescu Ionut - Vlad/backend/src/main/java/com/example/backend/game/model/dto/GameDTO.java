package com.example.backend.game.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {

    private Long id;
    private String title;
    private String thumbnail;
    private String description;
    private String gameURL;
    private String genre;
    private String platform;
    private String publisher;
    private String developer;
    private String releaseDate;
    private String freeToGameProfileURL;
}
