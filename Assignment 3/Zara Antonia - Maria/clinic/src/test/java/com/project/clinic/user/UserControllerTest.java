package com.project.clinic.user;

import com.project.clinic.BaseControllerTest;
import com.project.clinic.TestCreationFactory;
import com.project.clinic.treatment.TreatmentMapper;
import com.project.clinic.treatment.model.Treatment;
import com.project.clinic.treatment.model.dto.TreatmentDTO;
import com.project.clinic.user.dto.UserListDTO;
import com.project.clinic.user.dto.UserMinimalDTO;
import com.project.clinic.user.dto.UserRegisterDTO;
import com.project.clinic.skin_color.model.SkinColor;
import com.project.clinic.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static com.project.clinic.URLMapping.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @Mock
    private TreatmentMapper treatmentMapper;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new UserController(userService, treatmentMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findAll() throws Exception {
        List<UserListDTO> users = TestCreationFactory.listOf(UserListDTO.class);

        when(userService.findAll()).thenReturn(users);

        ResultActions response = performGet(USER + FIND_ALL);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(users));
    }

    @Test
    void allDermatologists() throws Exception {
        List<UserListDTO> users = TestCreationFactory.listOf(UserListDTO.class);

        when(userService.allDermatologistsForList()).thenReturn(users);

        ResultActions response = performGet(USER + DERMATOLOGISTS + FIND_ALL);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(users));
    }

    @Test
    void getTreatmentsOf() throws Exception {
        User user = TestCreationFactory.newUser();
        Treatment treatment = TestCreationFactory.newTreatment();
        TreatmentDTO dto = TreatmentDTO.builder()
                .title(treatment.getTitle())
                .points(treatment.getPoints())
                .category(treatment.getCategory().getName().name())
                .professionals(new HashSet<>())
                .build();

        user.setTreatments(Collections.singleton(treatment));

        when(userService.findById(user.getId())).thenReturn(user);
        when(treatmentMapper.toDto(treatment)).thenReturn(dto);

        List<TreatmentDTO> expected = new ArrayList<>();
        expected.add(dto);

        ResultActions response = performGetWithPathVariables(USER + DERMATOLOGISTS + ID_PART + "/treatments", user.getId());

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(expected));
    }

    @Test
    void findSkinColors() throws Exception {
        List<SkinColor> skinColors = TestCreationFactory.listOf(SkinColor.class);

        when(userService.findAllSkinColors()).thenReturn(skinColors);

        ResultActions response = performGet(USER + SKINCOLOR);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(skinColors));
    }

    @Test
    void create() throws Exception {
        UserMinimalDTO user = TestCreationFactory.newUserMinimalDTO();

        ResultActions response = performPostWithRequestBody(USER + ADD + DERMATOLOGISTS, user);

        response.andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        User user = TestCreationFactory.newUser();
        ResultActions response = performPostWithPathVariables(USER + DELETE + USERS_ID_PART, user.getId());

        response.andExpect(status().isOk());
    }

    @Test
    void edit() throws Exception {
        UserMinimalDTO user = TestCreationFactory.newUserMinimalDTO();

        UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                .id(user.getId())
                .password(user.getPassword())
                .name(user.getName())
                .points(0)
                .roles(new HashSet<>())
                .skinColor(null)
                .build();

        when(userService.edit(userRegisterDTO.getId(), userRegisterDTO)).thenReturn(userRegisterDTO);

        ResultActions response = performPutWithRequestBodyAndPathVariables(USER + UPDATE + USERS_ID_PART, user, user.getId());

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userRegisterDTO));
    }

    @Test
    void findUser() throws Exception {
        UserListDTO userListDTO = TestCreationFactory.newUserListDTO();

        when(userService.findDTOById(userListDTO.getId())).thenReturn(userListDTO);

        ResultActions response = performGetWithPathVariables(USER + FIND + USERS_ID_PART, userListDTO.getId());

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTO));
    }
}
