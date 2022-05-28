package com.example.youtubeish.video;

import com.example.youtubeish.user.UserRepository;
import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.user.model.User;
import com.example.youtubeish.video.dto.VideoDTO;
import com.example.youtubeish.video.dto.api.VideoAPIDTO;
import com.example.youtubeish.video.model.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.youtubeish.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class VideoServiceIntegrationTest {
    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        videoRepository.deleteAll();
    }

    @Test
    void create() {
        VideoAPIDTO videoAPIDTO = newVideoAPIDTO();
        User user = newUser();
        UserDTO userDTO = toDto(userRepository.save(user));
        Video video = videoService.create(videoAPIDTO, userDTO);
        assertNotNull(video);
        assertEquals(videoAPIDTO.getSnippet().getChannelTitle(), video.getChannelTitle());
    }

    @Test
    void getUploadedVideos() {
        User user = newUser();
        UserDTO userDTO = toDto(userRepository.save(user));
        videoService.create(newVideoAPIDTO(), userDTO);
        videoService.create(newVideoAPIDTO(), userDTO);
        videoService.create(newVideoAPIDTO(), userDTO);
        List<VideoDTO> videoDTOList = videoService.getUploadedVideos();
        assertEquals(3, videoDTOList.size());
    }

    @Test
    void getVideosFromUser() {
        User user = newUser();
        UserDTO userDTO = toDto(userRepository.save(user));
        videoService.create(newVideoAPIDTO(), userDTO);
        List<VideoDTO> videoDTOList = videoService.getVideosFromUser(userDTO.getId());
        assertEquals(1, videoDTOList.size());
    }
}
