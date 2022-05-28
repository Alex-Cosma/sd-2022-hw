package com.travel.hotel;

import com.travel.BaseControllerTest;
import com.travel.TestCreationFactory;
import com.travel.hotel.model.Hotel;
import com.travel.hotel.model.dto.HotelDTO;
import org.junit.jupiter.api.Test;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HotelControllerTest extends BaseControllerTest {
    @InjectMocks
    private HotelController controller;

    @Mock
    private HotelService hotelService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new HotelController(hotelService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allHotels() throws  Exception{
        List<HotelDTO> hotels = TestCreationFactory.listOf(Hotel.class);
        when(hotelService.findAll()).thenReturn(hotels);
        ResultActions response = performGet(HOTEL);

        response.andExpect(status().isOk()).andExpect(jsonContentToBe(hotels));
    }

    @Test
    void create() throws Exception {
        HotelDTO reqHotel = newHotelDTO();
        when(hotelService.create(reqHotel)).thenReturn(reqHotel);

        ResultActions result = performPostWithRequestBody(HOTEL, reqHotel);
        result.andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        long id =randomLong();
        doNothing().when(hotelService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(HOTEL + HOTEL_ID_PART, id);
        verify(hotelService, times(1)).delete(id);
        result.andExpect(status().isOk());
    }

    @Test
    void edit() throws Exception {
        long id =randomLong();
        HotelDTO reqHotel = newHotelDTO();
        when(hotelService.edit(id, reqHotel)).thenReturn(reqHotel);

        ResultActions resultActions = performPatchWithRequestBodyAndPathVariable(HOTEL + HOTEL_ID_PART,reqHotel, id);
        resultActions.andExpect(status().isOk());
    }
}
