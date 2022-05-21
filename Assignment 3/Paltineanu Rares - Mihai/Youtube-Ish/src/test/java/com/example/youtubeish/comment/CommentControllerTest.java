package com.example.youtubeish.comment;

import com.example.youtubeish.BaseControllerTest;
import com.example.youtubeish.TestCreationFactory;
import com.example.youtubeish.comment.dto.AddCommentDTO;
import com.example.youtubeish.comment.dto.CommentDTO;
import com.example.youtubeish.comment.model.Comment;
import com.example.youtubeish.security.dto.MessageResponse;
import com.example.youtubeish.user.dto.UserDTO;
import com.example.youtubeish.video.dto.VideoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.youtubeish.TestCreationFactory.*;
import static com.example.youtubeish.UrlMapping.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
public class CommentControllerTest  extends BaseControllerTest {
    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        commentController = new CommentController(commentService);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void getCommentFromVideo() throws Exception {
        Long id = randomLong();
        List<CommentDTO> commentDTOList = TestCreationFactory.listOf(CommentDTO.class);
        when(commentService.getAllCommentsFromVideo(id)).thenReturn(commentDTOList);
        ResultActions uploadVideoResult = performGetWithPathVariable(COMMENT + GET_COMMENTS, id);
        uploadVideoResult.andExpect(status().isOk())
                .andExpect(jsonContentToBe(commentDTOList));

    }

    @Test
    void addComment() throws Exception {
        UserDTO userDTO = newUserDto();
        VideoDTO videoDTO = newVideoDTO();
        AddCommentDTO addCommentDTO = AddCommentDTO.builder()
                .content(randomString())
                .user(userDTO)
                .video(videoDTO)
                .build();
        Comment comment = fromAddCommentDTO(addCommentDTO);
        when(commentService.create(addCommentDTO.getContent(), addCommentDTO.getUser(), addCommentDTO.getVideo()))
                .thenReturn(comment);
        ResultActions uploadVideoResult = performPostWithRequestBody(COMMENT + ADD_COMMENT, addCommentDTO);
        uploadVideoResult.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Comment added successfully!")));
    }
}
