package com.policeSection.driver;

import com.policeSection.BaseControllerTest;

import com.policeSection.driver.model.Driver;
import com.policeSection.driver.model.dto.DriverDTO;
import com.policeSection.fine.FineController;
import com.policeSection.fine.Model.dto.FineDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.policeSection.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DriverControllerTest extends BaseControllerTest {
    @InjectMocks
    private DriverController controller;

    @Mock
    private DriverService driverService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new DriverController(driverService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    @Test
    void allDrivers() throws Exception {
        DriverDTO driver1 = DriverDTO.builder().id(1L)
                .email("random")
                .name("random")
                .sectionNumber(3)
                .fines(new ArrayList<>())
                .build();
        DriverDTO driver2 = DriverDTO.builder().id(2L)
                .email("random")
                .name("random")
                .sectionNumber(3)
                .fines(new ArrayList<>())
                .build();
        List<DriverDTO> list = new ArrayList<>();
        list.add(driver1);
        list.add(driver2);
        when(driverService.findAll()).thenReturn(list);
        ResultActions response = performGet(DRIVERS);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(list));
    }
    @Test
    void create() throws Exception {
        DriverDTO driver1 = DriverDTO.builder().id(1L)
                .email("random")
                .name("random")
                .sectionNumber(3)
                .fines(new ArrayList<>())
                .build();

        when(driverService.create(driver1)).thenReturn(driver1);

        ResultActions result = performPostWithRequestBody(DRIVERS+CREATE_DRIVER, driver1);
        result.andExpect(status().isOk());
    }
    @Test
    void edit() throws Exception
    {
        DriverDTO driver1 = DriverDTO.builder().id(1L)
                .email("random")
                .name("random")
                .sectionNumber(3)
                .fines(new ArrayList<>())
                .build();

        when(driverService.create(driver1)).thenReturn(driver1);

        ResultActions result = performPostWithRequestBody(DRIVERS+CREATE_DRIVER, driver1);
        result.andExpect(status().isOk());
        driver1.setName("random test");
        when(driverService.edit(driver1.getId(),driver1)).thenReturn(driver1);
        ResultActions result2 = performPutWithRequestBodyAndPathVariables(DRIVERS+"/{id}", driver1,driver1.getId());
        result2.andExpect(status().isOk());
    }
    @Test
    void delete() throws Exception
    {
        DriverDTO driver1 = DriverDTO.builder().id(1L)
                .email("random")
                .name("random")
                .sectionNumber(3)
                .fines(new ArrayList<>())
                .build();

        when(driverService.create(driver1)).thenReturn(driver1);

        ResultActions result = performPostWithRequestBody(DRIVERS+CREATE_DRIVER, driver1);
        result.andExpect(status().isOk());
        doNothing().when(driverService).delete(driver1.getId());

        ResultActions result2 = performDeleteWIthPathVariable(DRIVERS +"/{id}", driver1.getId());
        result2.andExpect(status().isOk());
        verify(driverService, times(1)).delete(driver1.getId());
    }
    @Test
    void addFine() throws Exception {
        DriverDTO driver1 = DriverDTO.builder().id(1L)
                .email("random")
                .name("random")
                .sectionNumber(3)
                .fines(new ArrayList<>())
                .build();

        when(driverService.create(driver1)).thenReturn(driver1);

        FineDTO fine=FineDTO.builder().id(600L).description("AAA").type("AAA").price(100).build();

        ResultActions result = performPostWithRequestBody(DRIVERS+CREATE_DRIVER, driver1);
        result.andExpect(status().isOk());
        ResultActions result2 = performPutWithRequestBodyAndPathVariables(DRIVERS+GIVE_FINE, fine,driver1.getId());
        result2.andExpect(status().isOk());

    }
    @Test
    void removeFine() throws Exception {
        DriverDTO driver1 = DriverDTO.builder().id(1L)
                .email("random")
                .name("random")
                .sectionNumber(3)
                .fines(new ArrayList<>())
                .build();

        when(driverService.create(driver1)).thenReturn(driver1);

        FineDTO fine=FineDTO.builder().id(600L).description("AAA").type("AAA").price(100).build();

        ResultActions result = performPostWithRequestBody(DRIVERS+CREATE_DRIVER, driver1);
        result.andExpect(status().isOk());
        ResultActions result2 = performPutWithRequestBodyAndPathVariables(DRIVERS+GIVE_FINE, fine,driver1.getId());
        result2.andExpect(status().isOk());
        ResultActions result3 = performPutWithRequestBodyAndPathVariables(DRIVERS+PAY_FINE, fine,driver1.getId());
        result3.andExpect(status().isOk());

    }
}
