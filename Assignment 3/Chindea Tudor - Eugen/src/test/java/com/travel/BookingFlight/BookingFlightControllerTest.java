package com.travel.BookingFlight;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.travel.BookingFlight.model.dto.BookingFlightDTO;
import com.travel.BookingHotel.BookingHotelController;
import com.travel.BookingHotel.BookingHotelSevice;
import com.travel.flight.FlightService;
import com.travel.flight.FlightServiceTest;
import com.travel.flight.model.Flight;
import com.travel.flight.model.dto.FlightDTO;
import org.junit.jupiter.api.Test;


import com.travel.BaseControllerTest;
import com.travel.BookingHotel.model.dto.BookingHotelDTO;
import com.travel.TestCreationFactory;
import com.travel.hotel.HotelController;
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


class BookingFlightControllerTest extends BaseControllerTest{

    @InjectMocks
    private BookingFlightController controller;

    @Mock
    private BookingFlightService bookingFlightSevice;

    @Mock
    private FlightService flightService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BookingFlightController(bookingFlightSevice, flightService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    @Test
    void allFlights() throws Exception {
        List<FlightDTO> flights = TestCreationFactory.listOf(Flight.class);
        when(flightService.findAll()).thenReturn(flights);
        ResultActions response = performGet(BOOKINGF);

        response.andExpect(status().isOk()).andExpect(jsonContentToBe(flights));
    }

    @Test
    void bookFlight() throws Exception {
        BookingFlightDTO reqBooking = newBookingFlightDTO();
        when(bookingFlightSevice.bookFlight(reqBooking)).thenReturn(reqBooking);

        ResultActions resultActions = performPostWithRequestBody(BOOKINGF,reqBooking);
        resultActions.andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        long id =randomLong();
        doNothing().when(bookingFlightSevice).delete(id);

        ResultActions result = performDeleteWIthPathVariable(BOOKINGF + BOOKINGF_ID_PART, id);
        verify(bookingFlightSevice, times(1)).delete(id);
        result.andExpect(status().isOk());
    }
}