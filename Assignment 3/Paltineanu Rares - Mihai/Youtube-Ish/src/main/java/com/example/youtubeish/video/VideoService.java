package com.example.youtubeish.video;

import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.user.mapper.UserMapper;
import com.example.youtubeish.user.model.User;
import com.example.youtubeish.video.model.Video;
import com.example.youtubeish.video.model.dto.VideoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final UserMapper userMapper;
    public Video create(VideoDTO videoDTO, UserDTO user) {
        User fromDto = userMapper.fromDto(user);
        Video video = Video.builder()
                .channelTitle(videoDTO.getSnippet().getChannelTitle())
                .description(videoDTO.getSnippet().getDescription())
                .videoId(videoDTO.getId().getVideoId())
                .title(videoDTO.getSnippet().getTitle())
                .thumbnailUrl(videoDTO.getSnippet().getThumbnail().getMediumThumbnail().getUrl())
                .user(fromDto)
                .build();
        System.out.println(video.getUser().getId());
        return videoRepository.save(video);
    }

    public List<Video> getVideosFromUser(Long id) {
        return videoRepository.getAllByUserId(id);
    }
}
