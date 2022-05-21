package com.example.youtubeish.video.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Thumbnail {

    @JsonProperty("medium")
    private MediumThumbnail mediumThumbnail;
}
