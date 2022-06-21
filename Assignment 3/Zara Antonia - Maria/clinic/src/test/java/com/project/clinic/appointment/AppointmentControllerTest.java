package com.project.clinic.appointment;

import com.project.clinic.BaseControllerTest;
import com.project.clinic.TestCreationFactory;
import com.project.clinic.appointment.model.dto.AppointmentDTO;
import com.project.clinic.user.UserController;
import com.project.clinic.user.dto.UserListDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.clinic.URLMapping.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AppointmentControllerTest extends BaseControllerTest {

    @InjectMocks
    private AppointmentController controller;

    @Mock
    private AppointmentService appointmentService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new AppointmentController(appointmentService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allAppointments() throws Exception {
        List<AppointmentDTO> appointments = TestCreationFactory.listOf(AppointmentDTO.class);

        when(appointmentService.findAll(1L)).thenReturn(appointments);

        ResultActions response = performGetWithPathVariables(APPOINTMENT + FIND_ALL + ID_PART, 1L);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(appointments));
    }

    @Test
    void create() throws Exception {
        AppointmentDTO dto = TestCreationFactory.newAppointmentDTO();
        dto.setCustomerId(1L);

        when(appointmentService.create(dto)).thenReturn(dto);

        ResultActions response = performPostWithRequestBodyAndPathVariables(APPOINTMENT + ADD + ID_PART, dto, 1L);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(dto));
    }

    @Test
    void delete() throws Exception {
        AppointmentDTO dto = TestCreationFactory.newAppointmentDTO();

        ResultActions response = performPostWithPathVariables(APPOINTMENT + DELETE + ID_PART, dto.getId());

        response.andExpect(status().isOk());
    }

    @Test
    void findAppointment() throws Exception {
        AppointmentDTO dto = TestCreationFactory.newAppointmentDTO();

        when(appointmentService.findDTOById(dto.getId())).thenReturn(dto);

        ResultActions response = performGetWithPathVariables(APPOINTMENT + FIND + ID_PART, dto.getId());

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(dto));
    }
}
