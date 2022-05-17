package com.example.youtubeish.video.model.dto;

import com.example.youtubeish.video.model.dto.api.Thumbnail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoCommentDTO {

    private Long id;

    private String channelTitle;

    private String description;

    private String title;

    private Thumbnail thumbnail;

    private String videoId;

    private String thumbnailUrl;
}
