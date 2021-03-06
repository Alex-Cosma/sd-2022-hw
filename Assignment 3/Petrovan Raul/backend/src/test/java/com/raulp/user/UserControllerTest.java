package com.raulp.user;

import com.raulp.BaseControllerTest;
import com.raulp.TestCreationFactory;
import com.raulp.user.dto.instructor.InstructorMinimalDTO;
import com.raulp.user.dto.student.StudentMinimalDTO;
import com.raulp.user.dto.user.UserDetailsDTO;
import com.raulp.user.dto.user.UserListDTO;
import com.raulp.UrlMapping;
import com.raulp.user.dto.user.UserMinimalDTO;
import com.raulp.user.services.InstructorService;
import com.raulp.user.services.StudentService;
import com.raulp.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;
    @Mock
    private InstructorService instructorService;
    @Mock
    private StudentService studentService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        controller = new UserController(userService, studentService, instructorService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allUsers() throws Exception {
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        when(userService.allUsersForList()).thenReturn(userListDTOs);

        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.get(UrlMapping.API_PATH + UrlMapping.USER));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }

    @Test
    void myDetails() throws Exception {
        UserDetailsDTO userDetailsDTO = TestCreationFactory.newUserDetailsDTO();
        when(userService.myDetails(userDetailsDTO.getId())).thenReturn(userDetailsDTO);

        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.get(
                        UrlMapping.API_PATH + "/user/" + userDetailsDTO.getId()));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userDetailsDTO));
    }

    @Test
    void allStudents() throws Exception {
        List<UserMinimalDTO> students = TestCreationFactory.listOf(UserListDTO.class);
        when(studentService.getAllStudents()).thenReturn(students);

        ResultActions result = performGet(UrlMapping.API_PATH + UrlMapping.STUDENTS);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(students));
    }

    @Test
    void allInstructors() throws Exception {
        List<InstructorMinimalDTO> instructors =
                TestCreationFactory.listOf(InstructorMinimalDTO.class);
        when(instructorService.getAllInstructors()).thenReturn(instructors);

        ResultActions result = performGet(UrlMapping.API_PATH + UrlMapping.INSTRUCTORS);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(instructors));
    }

    @Test
    void getStudentsForInstructor() throws Exception {
        List<StudentMinimalDTO> students = TestCreationFactory.listOf(StudentMinimalDTO.class);
        when(instructorService.getStudentsForInstructor(1L)).thenReturn(students);

        ResultActions result =
                performGet(UrlMapping.API_PATH + "/my-students/1");
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(students));
    }

    @Test
    void getUnassignedStudents() throws Exception {
        List<StudentMinimalDTO> students = TestCreationFactory.listOf(StudentMinimalDTO.class);
        when(instructorService.getUnassignedStudents()).thenReturn(students);

        ResultActions result =
                performGet(UrlMapping.API_PATH + UrlMapping.UNASSIGNED_STUDENTS);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(students));
    }

    @Test
    void updateUser() {
        UserDetailsDTO userDetailsDTO = TestCreationFactory.newUserDetailsDTO();
        assertDoesNotThrow(() -> performPutWithRequestBody(UrlMapping.API_PATH + "/user/1",
                userDetailsDTO).andExpect(status().isOk()));

    }

    @Test
    void addStudent() {
        assertDoesNotThrow(() -> performPutWithoutRequestBody(
                UrlMapping.API_PATH + "/add-student" + "/1" + "/instructor/2").andExpect(
                status().isOk()));
    }
}