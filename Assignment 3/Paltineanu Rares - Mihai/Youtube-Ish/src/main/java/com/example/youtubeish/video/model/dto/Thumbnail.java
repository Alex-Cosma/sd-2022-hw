package com.example.youtubeish.video.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Thumbnail {

    @JsonProperty("medium")
    private MediumThumbnail mediumThumbnail;
}