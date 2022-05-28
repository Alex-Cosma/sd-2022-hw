package com.forum.post;

import com.forum.BaseControllerTest;
import com.forum.post.model.dto.PostDTO;
import com.forum.thread.model.dto.TopicDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.forum.TestCreationFactory.*;
import static com.forum.UrlMapping.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostControllerTest extends BaseControllerTest {
    @InjectMocks
    private PostController postController;

    @Mock
    private PostService postService;

    @BeforeEach
    public void setUp() {
        super.setUp();
        postController = new PostController(postService);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    void allItems() throws Exception {
        List<PostDTO> posts = listOf(PostDTO.class);
        when(postService.findAll()).thenReturn(posts);

        ResultActions response = performGet(POST);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(posts));
    }

    @Test
    void getByTopic() throws Exception {
        TopicDTO topic = newTopicDTO();
        List<PostDTO> posts = listOf(PostDTO.class);
        when(postService.findAllByTopicId(topic.getId())).thenReturn(posts);

        ResultActions response = performGetWithPathVariable(POST + OF_THREAD_ID_PART, topic.getId());

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(posts));
    }

    @Test
    void create() throws Exception {
        PostDTO post = newPostDTO();
        when(postService.create(post)).thenReturn(post);

        ResultActions response = performPostWithRequestBody(POST, post);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(post));
    }

    @Test
    void edit() throws Exception {
        final long id = randomLong();
        PostDTO post = newPostDTO();
        when(postService.edit(id, post)).thenReturn(post);

        ResultActions response = performPutWithRequestBodyAndPathVariables(POST + POST_ID_PART, post, id);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(post));
    }

    @Test
    void delete() throws Exception {
        PostDTO post = newPostDTO();
        when(postService.delete(post.getId())).thenReturn(post);

        ResultActions response = performDeleteWithPathVariables(POST + POST_ID_PART, post.getId());

        response.andExpect(status().isOk());
    }
}
