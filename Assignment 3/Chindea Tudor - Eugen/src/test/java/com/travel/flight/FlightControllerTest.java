package com.travel.flight;

import com.travel.TestCreationFactory;
import com.travel.flight.model.Flight;
import com.travel.flight.model.dto.FlightDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.travel.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.travel.TestCreationFactory.*;
import static com.travel.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FlightControllerTest extends BaseControllerTest {

    @InjectMocks
    private FlightController controller;

    @Mock
    private FlightService flightService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new FlightController(flightService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    void allFlights() throws Exception {
        List<FlightDTO> flights = TestCreationFactory.listOf(Flight.class);
        when(flightService.findAll()).thenReturn(flights);
        ResultActions response = performGet(FLIGHT);

        response.andExpect(status().isOk()).andExpect(jsonContentToBe(flights));
    }

    @Test
    void create() throws Exception {
        FlightDTO reqFlight = newFlightDTO();
        when(flightService.create(reqFlight)).thenReturn(reqFlight);

        ResultActions result = performPostWithRequestBody(FLIGHT, reqFlight);
        result.andExpect(status().isOk());

    }

    @Test
    void delete() throws Exception {
        long id =randomLong();
        doNothing().when(flightService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(FLIGHT + FLIGHT_ID_PART, id);
        verify(flightService, times(1)).delete(id);
        result.andExpect(status().isOk());
    }

    @Test
    void edit() throws Exception {
        long id =randomLong();
        FlightDTO reqFlight = newFlightDTO();
        when(flightService.edit(id, reqFlight)).thenReturn(reqFlight);

        ResultActions resultActions = performPatchWithRequestBodyAndPathVariable(FLIGHT + FLIGHT_ID_PART,reqFlight, id);
        resultActions.andExpect(status().isOk());
    }
}
