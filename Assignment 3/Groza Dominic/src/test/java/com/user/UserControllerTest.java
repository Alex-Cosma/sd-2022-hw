package com.user;

import com.BaseControllerTest;
import com.TestCreationFactory;
import com.post.PostController;
import com.post.model.dto.PostDto;
import com.security.dto.MessageResponse;
import com.user.dto.UserListDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static com.TestCreationFactory.randomLong;
import static com.TestCreationFactory.randomString;
import static com.UrlMapping.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        userController = new UserController( userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void allUsers() throws Exception {
        List<UserListDto> userListDtos = TestCreationFactory.listOf(UserListDto.class);

        when(userService.allUsersForList()).thenReturn(userListDtos);

        ResultActions response = mockMvc.perform(get(USERS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDtos));
    }

    @Test
    void create() throws Exception {
        UserListDto userListDto = TestCreationFactory.newUserListDto();

        when(userService.create(userListDto)).thenReturn(ResponseEntity.ok(new MessageResponse("User registered successfully!")));

        ResultActions response = performPostWithRequestBody(USERS, userListDto);

        response.andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {

        UserListDto userListDto = TestCreationFactory.newUserListDto();

        ResultActions response = performDeleteWIthPathVariable(USERS + ENTITY, userListDto.getId());

        response.andExpect(status().isOk());
    }

    @Test
    void edit() throws Exception {
        UserListDto userListDto = TestCreationFactory.newUserListDto();

        ResultActions response = performPutWithRequestBodyAndPathVariable(USERS + ENTITY, userListDto ,userListDto.getId());

        response.andExpect(status().isOk());
    }
}