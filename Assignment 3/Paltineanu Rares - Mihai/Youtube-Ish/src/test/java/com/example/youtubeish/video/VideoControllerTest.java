package com.example.youtubeish.video;

import com.example.youtubeish.BaseControllerTest;
import com.example.youtubeish.user.UserController;
import com.example.youtubeish.user.UserRepository;
import com.example.youtubeish.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class VideoControllerTest extends BaseControllerTest {
    @Mock
    private VideoController videoController;

    @Mock
    private VideoService videoService;

    @Mock
    private UserService userService;

    @Mock
    private VideoRepository videoRepository;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        videoController = new VideoController(videoService, userService);
        mockMvc = MockMvcBuilders.standaloneSetup(videoController).build();
    }

    @Test
    public void uploadVideo() {

    }
}
