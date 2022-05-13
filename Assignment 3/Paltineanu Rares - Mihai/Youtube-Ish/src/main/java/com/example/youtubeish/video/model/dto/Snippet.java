package com.example.youtubeish.video.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Snippet {

    @JsonProperty("channelTitle")
    private String channelTitle;

    @JsonProperty("description")
    private String description;

    @JsonProperty("title")
    private String title;

    @JsonProperty("thumbnails")
    private Thumbnail thumbnail;
}
