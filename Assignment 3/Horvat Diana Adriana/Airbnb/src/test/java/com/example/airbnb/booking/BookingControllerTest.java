package com.example.airbnb.booking;

import com.example.airbnb.TestCreationFactory;
import com.example.airbnb.accommodation.AccommodationController;
import com.example.airbnb.booking.model.dto.BookingDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;

import static com.example.airbnb.user.UrlMapping.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookingControllerTest extends com.example.bookstore.BaseControllerTest  {
    @InjectMocks
    private BookingController bookingController;

    @Mock
    private BookingService bookingService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        bookingController = new BookingController(bookingService);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void create() throws Exception {
        BookingDTO bookingDTO = TestCreationFactory.newBookingDTO();

        when(bookingService.save(bookingDTO)).thenReturn(bookingDTO);

        ResultActions result = performPostWithRequestBody(BOOKINGS, bookingDTO);

        verify(bookingService, times(1)).save(bookingDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(bookingDTO));

    }

    @Test
    void delete() throws Exception {
        BookingDTO bookingDTO = TestCreationFactory.newBookingDTO();

        doNothing().when(bookingService).delete(bookingDTO.getId());

        ResultActions result = performDeleteWithPathVariable(BOOKINGS + ENTITY, bookingDTO.getId());

        verify(bookingService, times(1)).delete(bookingDTO.getId());
        result.andExpect(status().isOk());
    }

}