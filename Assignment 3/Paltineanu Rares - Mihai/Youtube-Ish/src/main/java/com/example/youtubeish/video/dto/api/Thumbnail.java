package com.example.youtubeish.video.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Thumbnail {

    @JsonProperty("medium")
    private MediumThumbnail mediumThumbnail;
}
