package com.example.youtubeish.comment;

import com.example.youtubeish.TestCreationFactory;
import com.example.youtubeish.comment.dto.CommentDTO;
import com.example.youtubeish.comment.model.Comment;
import com.example.youtubeish.user.UserRepository;
import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.user.model.User;
import com.example.youtubeish.video.VideoRepository;
import com.example.youtubeish.video.dto.VideoDTO;
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
public class CommentServiceIntegrationTest {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    @BeforeEach
    void setup() {
        videoRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void create() {
        String content = randomString();
        User user = newUser();
        UserDTO userDTO = toDto(userRepository.save(user));
        user.setId(userDTO.getId());
        Video video = newVideo();
        video.setUser(user);
        video = videoRepository.save(video);
        VideoDTO videoDTO = TestCreationFactory.videoToDTO(video);
        Comment comment = commentService.create(content, userDTO, videoDTO);
        assertNotNull(comment);
    }

    @Test
    void getAllCommentsFromVideo() {
        String content = randomString();
        User user = newUser();
        UserDTO userDTO = toDto(userRepository.save(user));
        user.setId(userDTO.getId());
        Video video = newVideo();
        video.setUser(user);
        video = videoRepository.save(video);
        VideoDTO videoDTO = TestCreationFactory.videoToDTO(video);
        commentService.create(content, userDTO, videoDTO);
        content = randomString();
        commentService.create(content, userDTO, videoDTO);

        List<CommentDTO> commentList = commentService.getAllCommentsFromVideo(video.getId());
        assertNotNull(commentList);
        assertEquals(2, commentList.size());
    }
}
