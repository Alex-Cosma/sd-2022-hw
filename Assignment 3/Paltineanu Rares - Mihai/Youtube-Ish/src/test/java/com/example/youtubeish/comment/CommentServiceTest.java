package com.example.youtubeish.comment;

import com.example.youtubeish.BaseControllerTest;
import com.example.youtubeish.comment.dto.CommentDTO;
import com.example.youtubeish.comment.mapper.CommentMapper;
import com.example.youtubeish.comment.model.Comment;
import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.user.mapper.UserMapper;
import com.example.youtubeish.video.dto.VideoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.youtubeish.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class CommentServiceTest extends BaseControllerTest {
    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        commentService = new CommentService(commentRepository, commentMapper, userMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(commentService).build();
    }

    @Test
    void getAllCommentsFromVideo() {
        Long id = randomLong();
        List<Comment> commentList = List.of(
                newComment(),
                newComment(),
                newComment()
        );
        when(commentRepository.getAllByVideoId(id)).thenReturn(commentList);
        for(Comment comment: commentList) {
            when(commentMapper.toDto(comment)).thenReturn(fromComment(comment));
        }
        List<CommentDTO> commentDTOList = commentService.getAllCommentsFromVideo(id);
        assertEquals(commentDTOList.size(), commentList.size());
        assertEquals(commentDTOList.get(0).getAuthor(), commentList.get(0).getUser().getUsername());
    }

    @Test
    void create() {
        String content = randomString();
        UserDTO userDTO = newUserDto();
        VideoDTO videoDTO = newVideoDTO();
        videoDTO.setUser(userDTO);
        Comment comment = Comment.builder()
                .content(content)
                .user(fromDto(userDTO))
                .video(videoFromDTO(videoDTO))
                .build();
        when(userMapper.fromDto(userDTO)).thenReturn(fromDto(userDTO));
        when(commentRepository.save(comment)).thenReturn(comment);
        assertNotNull(commentService.create(content, userDTO, videoDTO));
    }
}
