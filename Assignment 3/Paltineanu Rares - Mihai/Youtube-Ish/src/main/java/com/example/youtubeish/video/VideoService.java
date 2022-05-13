package com.example.youtubeish.video;

import com.example.youtubeish.user.model.User;
import com.example.youtubeish.video.model.Video;
import com.example.youtubeish.video.model.dto.VideoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    public void create(VideoDTO videoDTO, Long userId) {
        Video video = Video.builder()
                .channelTitle(videoDTO.getSnippet().getChannelTitle())
                .description(videoDTO.getSnippet().getDescription())
                .videoId(videoDTO.getId().getVideoId())
                .title(videoDTO.getSnippet().getTitle())
                .thumbnailUrl(videoDTO.getSnippet().getThumbnail().getMediumThumbnail().getUrl())
                .build();
    }
}
