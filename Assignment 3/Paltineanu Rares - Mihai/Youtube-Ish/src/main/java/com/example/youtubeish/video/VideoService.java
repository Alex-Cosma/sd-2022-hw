package com.example.youtubeish.video;

import com.example.youtubeish.playlist.PlaylistRepository;
import com.example.youtubeish.playlist.PlaylistService;
import com.example.youtubeish.playlist.mapper.PlaylistMapper;
import com.example.youtubeish.playlist.model.Playlist;
import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.user.mapper.UserMapper;
import com.example.youtubeish.user.model.User;
import com.example.youtubeish.video.mapper.VideoMapper;
import com.example.youtubeish.video.model.Video;
import com.example.youtubeish.video.dto.VideoDTO;
import com.example.youtubeish.video.dto.api.VideoAPIDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final UserMapper userMapper;
    private final VideoMapper videoMapper;
    private final PlaylistService playlistService;

    public Video create(VideoAPIDTO videoDTO, UserDTO user) {
        User fromDto = userMapper.fromDto(user);
        Video video = Video.builder()
                .channelTitle(videoDTO.getSnippet().getChannelTitle())
                .description(videoDTO.getSnippet().getDescription())
                .videoId(videoDTO.getId().getVideoId())
                .title(videoDTO.getSnippet().getTitle())
                .thumbnailUrl(videoDTO.getSnippet().getThumbnail().getMediumThumbnail().getUrl())
                .user(fromDto)
                .build();
        return videoRepository.save(video);
    }

    public List<VideoDTO> getUploadedVideos() {
        return videoRepository.findAll().stream().map(videoMapper::toDto).collect(Collectors.toList());
    }

    public List<VideoDTO> getVideosFromUser(Long id) {
        return videoRepository.getAllByUserId(id).stream().map(videoMapper::toDto).collect(Collectors.toList());
    }

    public void deleteVideoById(Long id) {
        List<Playlist> playlists = playlistService.getVideoPlaylists(id);
        Video video = videoRepository.findById(id).orElseThrow();
        for(Playlist playlist: playlists) {
            playlist.removeVideo(video);
            if(playlist.getVideos().isEmpty()) {
                playlistService.deletePlaylistById(playlist.getId());
            }
        }
        this.videoRepository.deleteById(id);
    }
}
