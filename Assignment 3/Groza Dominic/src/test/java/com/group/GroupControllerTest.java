package com.group;

import com.BaseControllerTest;
import com.TestCreationFactory;
import com.group.model.Group;
import com.group.model.dto.GroupDto;
import com.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.TestCreationFactory.randomLong;
import static com.UrlMapping.ENTITY;
import static com.UrlMapping.GROUPS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GroupControllerTest extends BaseControllerTest {

    @Mock
    private GroupService groupService;

    @Mock
    private UserService userService;

    @InjectMocks
    private GroupController groupController;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        groupController = new GroupController(groupService, userService);
        mockMvc = MockMvcBuilders.standaloneSetup(groupController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void allGroups() throws Exception {

        List<GroupDto> groupDtoList = TestCreationFactory.listOf(GroupDto.class);

        when(groupService.findAll()).thenReturn(groupDtoList);

        ResultActions response = mockMvc.perform(get(GROUPS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(groupDtoList));
    }

    @Test
    void addUser() throws Exception {
        long id = randomLong();
        GroupDto groupDto = GroupDto.builder()
                .id(id)
                .name("name")
                .build();
        when(userService.addToGroup(id, groupDto)).thenReturn(groupDto);

        ResultActions response =performPatchWithRequestBodyAndPathVariable(GROUPS+ENTITY,groupDto,id);

        response.andExpect(status().isOk());
    }


    @Test
    void getGroup() throws Exception {
        GroupDto group = new GroupDto();
        ResultActions response =performGetWithPathVariable(GROUPS+ENTITY,group.getId());

        System.out.println(response.toString());
        //nested json response, nu am reusit sa gasesc body ul, dar statusul e ok
        response.andExpect(status().isOk());
    }
}