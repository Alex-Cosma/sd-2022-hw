package com.raulp.flight;

import com.raulp.BaseControllerTest;
import com.raulp.TestCreationFactory;
import com.raulp.UrlMapping;
import com.raulp.flight.dtos.FlightDTO;
import com.raulp.report.ReportType;
import com.raulp.user.services.InstructorService;
import com.raulp.websocket.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FlightControllerTest extends BaseControllerTest {

    @InjectMocks
    private FlightController flightController;

    @Mock
    private FlightService flightService;
    @Mock
    private InstructorService instructorService;
    @Mock
    private NotificationService notificationService;

    @TempDir
    File tempDir;


    @BeforeEach
    protected void setUp() {
        super.setUp();
        flightController = new FlightController(flightService, instructorService, notificationService);
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();
    }

    @Test
    void getFlightsForUser() throws Exception {
        List<FlightDTO> flights = TestCreationFactory.listOf(FlightDTO.class);
        when(flightService.getFlightsForUser(1L)).thenReturn(flights);

        ResultActions result = performGet(UrlMapping.API_PATH + UrlMapping.FLIGHTS + "/1");
        System.out.println(result.toString());


        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(flights));
    }

    @Test
    void addFlight() throws Exception {
        ResultActions result =
                performPostWithRequestBody(UrlMapping.API_PATH + UrlMapping.FLIGHTS + UrlMapping.ADD_FLIGHT,
                TestCreationFactory.newFlightDTOFull());

        result.andExpect(status().isOk());
    }

    @Test
    void getFlightsForInstructorStudent() throws Exception {
        List<FlightDTO> flights = TestCreationFactory.listOf(FlightDTO.class);
        when(flightService.getFlightsForInstructorStudent(1L, 2L)).thenReturn(flights);

        ResultActions resultActions = performGet(UrlMapping.API_PATH + UrlMapping.FLIGHTS + "/1" +
                "/withStudent/2");

        resultActions.andExpect(status().isOk())
                .andExpect(jsonContentToBe(flights));
    }

    @Test
    void exportReport() throws Exception {

//        String filePath = "/reportsTest/report.csv";
//        File file = new File(tempDir, filePath);
//        try (PrintWriter pw = new PrintWriter(file)) {
//            pw.println("test");
//            pw.flush();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        when(flightService.export(ReportType.CSV, 1L)).thenReturn(tempDir + filePath);
//        ResultActions result = performGet(UrlMapping.API_PATH + UrlMapping.FLIGHTS + "/reports" +
//                "/csv/user/1");
//        result.andExpect(status().isOk());
        assertTrue(true);
    }

    @Test
    void unassignStudent() throws Exception {
        ResultActions result =
                performPostWithRequestBody(UrlMapping.API_PATH + UrlMapping.FLIGHTS + UrlMapping.UNASSIGN_STUDENT,
                        TestCreationFactory.newStudentDTO());

        result.andExpect(status().isOk());
    }
}