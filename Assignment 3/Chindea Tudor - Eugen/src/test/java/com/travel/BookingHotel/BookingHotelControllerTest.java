package com.travel.BookingHotel;

import com.travel.BaseControllerTest;
import com.travel.BookingHotel.model.dto.BookingHotelDTO;
import com.travel.TestCreationFactory;

import com.travel.hotel.HotelService;
import com.travel.hotel.model.Hotel;
import com.travel.hotel.model.dto.HotelDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Set;

import static com.travel.TestCreationFactory.*;
//import static com.travel.UrlMapping.BOOKINGH2;

import static com.travel.UrlMapping.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookingHotelControllerTest extends BaseControllerTest {
    @InjectMocks
    private BookingHotelController controller;

    @Mock
    private BookingHotelSevice bookingHotelSevice;

    @Mock
    private HotelService hotelService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BookingHotelController(bookingHotelSevice, hotelService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allHotels() throws Exception {
        List<HotelDTO> hotels = TestCreationFactory.listOf(Hotel.class);
        when(hotelService.findAll()).thenReturn(hotels);
        ResultActions response = performGet(BOOKINGH);

        response.andExpect(status().isOk()).andExpect(jsonContentToBe(hotels));
    }
//    @Test
//    void allBookings() throws Exception {
//        List<BookingHotelDTO> bookings = TestCreationFactory.listOf(Hotel.class);
//        when(bookingHotelSevice.findAll()).thenReturn(bookings);
//        ResultActions response = performGet(BOOKINGH2);
//
//        response.andExpect(status().isOk()).andExpect(jsonContentToBe(bookings));
//    }
    @Test
    void bookRoom() throws Exception {
        BookingHotelDTO reqBooking = newBookingHotelDTO();
        when(bookingHotelSevice.bookRoom(reqBooking)).thenReturn(reqBooking);

        ResultActions resultActions = performPostWithRequestBody(BOOKINGH,reqBooking);
        resultActions.andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        long id =randomLong();
        doNothing().when(bookingHotelSevice).delete(id);

        ResultActions result = performDeleteWIthPathVariable(BOOKINGH + BOOKINGH_ID_PART, id);
        verify(bookingHotelSevice, times(1)).delete(id);
        result.andExpect(status().isOk());
    }


}