package com.raulp.weather;

import com.raulp.BaseControllerTest;
import com.raulp.UrlMapping;
import com.raulp.weather.models.MetarTaf;
import com.raulp.weather.models.metarDecoded.MetarDecoded;
import com.raulp.weather.models.tafDecoded.TafDecoded;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class WeatherApiControllerTest extends BaseControllerTest {

    @Autowired
    private WeatherApiController controller;

    @Autowired
    private WeatherService weatherService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new WeatherApiController(weatherService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getMetar() throws Exception {
           ResultActions result =
                performGet(UrlMapping.WEATHER_API + UrlMapping.METAR +
                        "/LRBM");
           result.andExpect(status().isOk());
           MetarTaf metarTaf = controller.getMetar("LRBM");
           assertNotNull(metarTaf);
    }

    @Test
    void getMetarDecoded() throws Exception {
        ResultActions result =
                performGet(UrlMapping.WEATHER_API + UrlMapping.METAR +
                        "/LRBM/decoded");
        result.andExpect(status().isOk());
        MetarDecoded metarDecoded = controller.getMetarDecoded("LRBM");
        assertNotNull(metarDecoded);
    }

    @Test
    void getTaf() throws Exception {
        ResultActions result =
                performGet(UrlMapping.WEATHER_API + UrlMapping.TAF +
                        "/LRBM");
        result.andExpect(status().isOk());
        MetarTaf metarTaf = controller.getTaf("LRBM");
        assertNotNull(metarTaf);
    }

    @Test
    void getTafDecoded() throws Exception {
        ResultActions result =
                performGet(UrlMapping.WEATHER_API + UrlMapping.TAF +
                        "/LRBM/decoded");
        result.andExpect(status().isOk());
        TafDecoded tafDecoded = controller.getTafDecoded("LRBM");
        assertNotNull(tafDecoded);
    }
}