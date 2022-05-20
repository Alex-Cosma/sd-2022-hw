package com.example.youtubeish.video;

import com.example.youtubeish.BaseControllerTest;
import com.example.youtubeish.TestCreationFactory;
import com.example.youtubeish.playlist.PlaylistService;
import com.example.youtubeish.security.dto.MessageResponse;
import com.example.youtubeish.user.UserController;
import com.example.youtubeish.user.UserRepository;
import com.example.youtubeish.user.UserService;
import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.user.mapper.UserMapper;
import com.example.youtubeish.user.model.User;
import com.example.youtubeish.video.dto.UploadVideoDTO;
import com.example.youtubeish.video.dto.VideoDTO;
import com.example.youtubeish.video.dto.api.VideoAPIDTO;
import com.example.youtubeish.video.model.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.example.youtubeish.TestCreationFactory.*;
import static com.example.youtubeish.UrlMapping.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VideoControllerTest extends BaseControllerTest {
    @InjectMocks
    private VideoController videoController;

    @Mock
    private VideoService videoService;

    @Mock
    private UserService userService;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PlaylistService playlistService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        videoController = new VideoController(videoService, userService);
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(videoController).build();
    }

    @Test
    public void getApiVideos() throws Exception {
        assertEquals(5, videoController.allVideos("AIzaSyDhfMBuo6UdD4x2327O7sDT7BKbYuMzb20",
                "pewdiepie",
                "video",
                "snippet",
                "5").getVideoDTOList().size());
    }

    @Test
    void uploadVideo() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .email("myemail@aaa.com")
                .username("username")
                .password("password")
                .build();
        VideoAPIDTO videoAPIDTO = videoController.allVideos("AIzaSyDhfMBuo6UdD4x2327O7sDT7BKbYuMzb20",
                "pewdiepie",
                "video",
                "snippet",
                "5").getVideoDTOList().get(0);

        assertNotNull(videoAPIDTO);

        Video video = Video.builder()
                .channelTitle(videoAPIDTO.getSnippet().getChannelTitle())
                .description(videoAPIDTO.getSnippet().getDescription())
                .videoId(videoAPIDTO.getId().getVideoId())
                .title(videoAPIDTO.getSnippet().getTitle())
                .thumbnailUrl(videoAPIDTO.getSnippet().getThumbnail().getMediumThumbnail().getUrl())
                .user(userMapper.fromDto(userDTO))
                .build();

        UploadVideoDTO uploadVideoDTO = UploadVideoDTO.builder()
                .user(userDTO)
                .video(videoAPIDTO)
                .build();
        when(videoRepository.save(video)).thenReturn(video);

        ResultActions uploadVideoResult = performPostWithRequestBody(VIDEOS + UPLOAD_VIDEO, uploadVideoDTO);
        uploadVideoResult.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Video uploaded successfully!")));
    }

    @Test
    void deleteVideoById() throws Exception {
        Long id = randomLong();
        Video video = Video.builder()
                .id(id)
                .channelTitle(randomString())
                .description(randomString())
                .videoId(randomString())
                .title(randomString())
                .thumbnailUrl(randomString())
                .user(userMapper.fromDto(newUserDto()))
                .build();
        doNothing().when(videoService).deleteVideoById(id);
        when(playlistService.getVideoPlaylists(id)).thenReturn(new ArrayList<>());
        when(videoRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(video));
        doNothing().when(videoRepository).deleteById(id);

        ResultActions uploadVideoResult = performDeleteWIthPathVariable(VIDEOS + DELETE_VIDEO, id);
        uploadVideoResult.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Video deleted successfully!")));
    }

    @Test
    void getVideosFromUser() throws Exception {
        User user = newUser();
        when(userService.getUserByUsername(user.getUsername())).thenReturn(user);
        List<VideoDTO> videoDTOList = TestCreationFactory.listOf(VideoDTO.class);
        when(videoService.getVideosFromUser(user.getId())).thenReturn(videoDTOList);
        assertEquals(videoDTOList, videoController.getVideoFromUser(user.getUsername()));

    }

    @Test
    void getUploadedVideos() throws Exception {

    }
}
