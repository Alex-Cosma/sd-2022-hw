package com.travel.city;

import com.travel.BaseControllerTest;
import com.travel.TestCreationFactory;
import com.travel.city.model.City;
import com.travel.city.model.dto.CityDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.travel.TestCreationFactory.*;
import static com.travel.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CityControllerTest extends BaseControllerTest {
   @InjectMocks
    private CityController controller;

   @Mock
    private CityService cityService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new CityController(cityService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allCities() throws Exception {
        List<CityDTO> cities = TestCreationFactory.listOf(City.class);
        when(cityService.findAll()).thenReturn(cities);
        ResultActions response = performGet(ITEMS);

        response.andExpect(status().isOk()).andExpect(jsonContentToBe(cities));
    }

    @Test
    void create() throws Exception {
        CityDTO reqCity = newCityDTO();
        when(cityService.create(reqCity)).thenReturn(reqCity);

        ResultActions result = performPostWithRequestBody(ITEMS, reqCity);
        result.andExpect(status().isOk());

    }

    @Test
    void delete() throws Exception{
        long id =randomLong();
        doNothing().when(cityService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(ITEMS + ITEMS_ID_PART, id);
        verify(cityService, times(1)).delete(id);
        result.andExpect(status().isOk());
    }

    @Test
    void edit() throws Exception{
        long id =randomLong();
        CityDTO reqCity = newCityDTO();
        when(cityService.edit(id, reqCity)).thenReturn(reqCity);

        ResultActions resultActions = performPatchWithRequestBodyAndPathVariable(ITEMS + ITEMS_ID_PART,reqCity, id);
        resultActions.andExpect(status().isOk());
    }



}
