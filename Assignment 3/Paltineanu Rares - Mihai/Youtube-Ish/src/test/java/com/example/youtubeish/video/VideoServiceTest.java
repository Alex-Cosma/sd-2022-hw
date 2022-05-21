package com.example.youtubeish.video;

import com.example.youtubeish.BaseControllerTest;
import com.example.youtubeish.playlist.PlaylistService;
import com.example.youtubeish.playlist.model.Playlist;
import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.user.mapper.UserMapper;
import com.example.youtubeish.video.dto.VideoDTO;
import com.example.youtubeish.video.dto.api.*;
import com.example.youtubeish.video.mapper.VideoMapper;
import com.example.youtubeish.video.model.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.example.youtubeish.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class VideoServiceTest extends BaseControllerTest {
    @InjectMocks
    private VideoService videoService;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private VideoMapper videoMapper;

    @Mock
    private PlaylistService playlistService;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        videoService = new VideoService(videoRepository, userMapper, videoMapper, playlistService);
        mockMvc = MockMvcBuilders.standaloneSetup(videoService).build();
    }

    @Test
    void create() {
        UserDTO userDTO = newUserDto();
        Video video = newVideo();
        video.setUser(fromDto(userDTO));
        VideoAPIDTO videoAPIDTO = VideoAPIDTO.builder()
                .id(VideoID.builder()
                        .videoId(video.getVideoId())
                        .build())
                .snippet(Snippet.builder()
                        .channelTitle(video.getChannelTitle())
                        .title(video.getTitle())
                        .description(video.getDescription())
                        .thumbnail(Thumbnail.builder()
                                .mediumThumbnail(MediumThumbnail.builder()
                                        .url(video.getThumbnailUrl())
                                        .build())
                                .build())
                        .build())
                .build();
        when(userMapper.fromDto(userDTO)).thenReturn(fromDto(userDTO));
        when(videoRepository.save(video)).thenReturn(video);
        assertEquals(video, videoService.create(videoAPIDTO, userDTO));
    }

    @Test
    void getUploadedVideos() {
        List<Video> videosList = List.of(newVideo(), newVideo(), newVideo());
        when(videoRepository.findAll()).thenReturn(videosList);
        for(Video video: videosList) {
            when(videoMapper.toDto(video)).thenReturn(videoToDTO(video));
        }
        List<VideoDTO> videoDTOList = videoService.getUploadedVideos();
        assertEquals(videoDTOList.size(), videosList.size());
        assertEquals(videoDTOList.get(0).getVideoId(), videosList.get(0).getVideoId());
        assertEquals(videoDTOList.get(1).getTitle(), videosList.get(1).getTitle());
    }

    @Test
    void getVideosFromUser() {
        Long id = randomLong();
        List<Video> videosList = List.of(newVideo(), newVideo(), newVideo());
        when(videoRepository.getAllByUserId(id)).thenReturn(videosList);
        for(Video video: videosList) {
            when(videoMapper.toDto(video)).thenReturn(videoToDTO(video));
        }

        List<VideoDTO> videoDTOList = videoService.getVideosFromUser(id);
        assertEquals(videoDTOList.size(), videosList.size());
        assertEquals(videoDTOList.get(0).getVideoId(), videosList.get(0).getVideoId());
        assertEquals(videoDTOList.get(1).getTitle(), videosList.get(1).getTitle());
    }

    @Test
    void deleteVideoById() {
        List<Video> videosList = new ArrayList<>();
        videosList.add(newVideo());
        videosList.add(newVideo());
        videosList.add(newVideo());
        Playlist playlist = Playlist.builder()
                        .id(randomLong())
                        .videos(videosList)
                        .user(newUser())
                .build();

        when(playlistService.getVideoPlaylists(videosList.get(0).getId())).thenReturn(List.of(playlist));
        when(videoRepository.findById(videosList.get(0).getId())).thenReturn(java.util.Optional.ofNullable(videosList.get(0)));
        doNothing().when(playlistService).deletePlaylistById(playlist.getId());
        doNothing().when(videoRepository).deleteById(videosList.get(0).getId());

        videoService.deleteVideoById(videosList.get(0).getId());
    }
}
