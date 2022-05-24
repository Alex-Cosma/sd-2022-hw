package com.post;

import com.BaseControllerTest;
import com.TestCreationFactory;
import com.post.model.dto.PostDto;
import com.user.UserRepository;
import com.user.UserService;
import com.user.dto.UserListDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static com.TestCreationFactory.*;
import static com.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostControllerTest extends BaseControllerTest {

    @Mock
    private PostService postService;
    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        postController = new PostController(postService, userService);
        mockMvc = MockMvcBuilders.standaloneSetup(postController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void allPosts() throws Exception {
        List<PostDto> postDtoList = TestCreationFactory.listOf(PostDto.class);

        when(postService.findAll()).thenReturn(postDtoList);

        ResultActions response = mockMvc.perform(get(POSTS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(postDtoList));
    }

    @Test
    void create() throws Exception {
        PostDto postDto = TestCreationFactory.newPostDto();
        UserListDto userListDto=newUserListDto();
        when(userService.get(1L)).thenReturn(userListDto);

        when(postService.create(1L,postDto)).thenReturn(postDto);
        ResultActions response = performPostWithRequestBody(POSTS, postDto);

        response.andExpect(status().isOk()).
                andExpect(jsonContentToBe(postDto));
    }

    @Test
    void edit() throws Exception {

        PostDto postDto = PostDto.builder()
                .id(randomLong())
                .created_at(Date.from(Instant.now()))
                .body(randomString())
                .likes(22L)
                .disLikes(32L)
                .build();
        ResultActions response = performPutWithRequestBodyAndPathVariable(POSTS + ENTITY, postDto, postDto.getId());

        response.andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        PostDto postDto = newPostDto();

        ResultActions response = performDeleteWIthPathVariable(POSTS + ENTITY,postDto.getId());

        response.andExpect(status().isOk());
    }

    @Test
    void getPost() throws Exception {
        PostDto postDto = newPostDto();

        ResultActions response = performGetWithPathVariable(POSTS + ENTITY, postDto.getId());

        response.andExpect(status().isOk());
    }
}