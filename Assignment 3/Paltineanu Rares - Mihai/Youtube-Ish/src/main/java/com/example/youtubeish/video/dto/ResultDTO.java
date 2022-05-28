package com.example.youtubeish.video.dto;

import com.example.youtubeish.video.dto.api.VideoAPIDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResultDTO {
    @JsonProperty("items")
    private List<VideoAPIDTO> videoDTOList = new ArrayList<>();
}
