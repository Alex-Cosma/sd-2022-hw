package com.lab4.demo.track.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackDTO {
    private Long id;
    private String title;
    private String link;
    private int duration;
    private Boolean explicit_lyrics;
    private String preview;
    private String artist;
    private String album;
}
