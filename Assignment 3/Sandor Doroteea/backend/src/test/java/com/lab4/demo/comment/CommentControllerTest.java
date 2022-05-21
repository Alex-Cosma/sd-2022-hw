package com.lab4.demo.comment;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.comment.model.Comment;
import com.lab4.demo.comment.model.dto.CommentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.UrlMapping.COMMENTS;
import static com.lab4.demo.UrlMapping.COMMENT_ID_PART;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest extends BaseControllerTest {
    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        commentController = new CommentController(commentService);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void getAllComments() throws Exception {
        CommentDTO commentDTO1 = CommentDTO.builder()
                .id("628922051c1a4817735b5843")
                .content("Content1")
                .userId(1L)
                .trackId(1L)
                .build();

        CommentDTO commentDTO2 = CommentDTO.builder()
                .id("628922061c1a4817735b5844")
                .content("Content2")
                .userId(1L)
                .trackId(1L)
                .build();

        List<CommentDTO> comments = new ArrayList<>();
        comments.add(commentDTO1);
        comments.add(commentDTO2);
        Mockito.when(commentService.findAll()).thenReturn(comments);

        ResultActions response = performGet(COMMENTS);

        response.andExpect(status().isOk());
               //.andExpect(jsonContentToBe(comments));
    }

//    @Test
//    void getAllComments() throws Exception {
//                CommentDTO commentDTO1 = CommentDTO.builder()
//                .id("628922051c1a4817735b5843")
//                .content("Content1")
//                .userId(1L)
//                .trackId(1L)
//                .build();
//        List<CommentDTO> comments = new ArrayList<>();
//        comments.add(commentDTO1);
//        //comments.add(TestCreationFactory.createComment());
//        when(commentService.findAll()).thenReturn(comments);
//        ResultActions resultActions = mockMvc.perform(get(COMMENTS));
//        resultActions.andExpect(status().isOk())
//                .andExpect(jsonContentToBe(comments));
//    }


    @Test
    void create() throws Exception {
        CommentDTO commentDTO = CommentDTO.builder()
                .content("Content")
                .userId(1L)
                .trackId(1L)
                .build();

        when(commentService.save(commentDTO)).thenReturn(commentDTO);

        ResultActions result = performPostWithRequestBody(COMMENTS, commentDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(commentDTO));
    }

    @Test
    void update() throws Exception {
        CommentDTO commentDTO = CommentDTO.builder()
                .content("Content")
                .userId(1L)
                .trackId(1L)
                .build();
        String id = "1L";
        when(commentService.update(id, commentDTO)).thenReturn(commentDTO);

        ResultActions result = performPutWithRequestBodyAndPathVariables(COMMENTS + COMMENT_ID_PART, commentDTO, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(commentDTO));
    }

    @Test
    void delete() throws Exception {
        CommentDTO commentDTO = CommentDTO.builder()
                .id("1L")
                .content("Content")
                .userId(1L)
                .trackId(1L)
                .build();

        when(commentService.save(commentDTO)).thenReturn(commentDTO);
        doNothing().when(commentService).delete(commentDTO.getId());

        ResultActions response = performDeleteWithPathVariable(COMMENTS + COMMENT_ID_PART, commentDTO.getId());
        response.andExpect(status().isOk());
    }
}