package com.raulp.flight;

import com.raulp.BaseControllerTest;
import com.raulp.TestCreationFactory;
import com.raulp.UrlMapping;
import com.raulp.flight.dtos.AirportDTO;
import com.raulp.flight.dtos.PlaneDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AssetControllerTest extends BaseControllerTest {

    @InjectMocks
    private AssetController assetController;

    @Mock
    private FlightService flightService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        assetController = new AssetController(flightService);
        mockMvc = MockMvcBuilders.standaloneSetup(assetController).build();
    }

    @Test
    void getAirplanes() throws Exception {
        List<PlaneDTO> planes = TestCreationFactory.listOf(PlaneDTO.class);
        when(flightService.getAirplanes()).thenReturn(planes);

        ResultActions result = performGet(UrlMapping.API_PATH + UrlMapping.AIRPLANES);

        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(planes));
    }

    @Test
    void getAirports() throws Exception {
        List<AirportDTO> airports = TestCreationFactory.listOf(AirportDTO.class);
        when(flightService.getAirports()).thenReturn(airports);

        ResultActions result = performGet(UrlMapping.API_PATH + UrlMapping.AIRPORTS);

        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(airports));
    }
}