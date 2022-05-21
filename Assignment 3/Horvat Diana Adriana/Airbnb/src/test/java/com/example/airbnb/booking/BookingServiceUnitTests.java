package com.example.airbnb.booking;

import com.example.airbnb.TestCreationFactory;
import com.example.airbnb.accommodation.AccommodationService;
import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.booking.model.Booking;
import com.example.airbnb.booking.model.dto.BookingDTO;
import com.example.airbnb.user.UserService;
import com.example.airbnb.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class BookingServiceUnitTests {
    @InjectMocks
    private BookingService bookingService;

    @Mock
    private AccommodationService accommodationService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserService userService;

    @Mock
    private BookingMapper bookingMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookingService = new BookingService(bookingRepository, accommodationService, userService, bookingMapper);
    }

    @Test
    void save() throws ParseException {
        User user = TestCreationFactory.user();
        Accommodation accommodation = TestCreationFactory.accommodationWithUser(user);
        BookingDTO bookingDTO = TestCreationFactory.bookingDTO(accommodation.getId(), user.getId());
        Booking booking = TestCreationFactory.booking(accommodation, user, bookingDTO.getId());

        when(bookingMapper.fromDto(bookingDTO)).thenReturn(booking);
        when(accommodationService.findById(bookingDTO.getAccommodation_id())).thenReturn(accommodation);
        when(userService.findById(bookingDTO.getGuest_id())).thenReturn(user);
        when(bookingMapper.toDto(booking)).thenReturn(bookingDTO);
        when(bookingRepository.save(booking)).thenReturn(booking);

        BookingDTO savedBookingDTO = bookingService.save(bookingDTO);

        assertEquals(bookingDTO.getId(), savedBookingDTO.getId());
    }

    @Test
    void delete() throws ParseException {
        Booking booking = TestCreationFactory.newBooking();

        doNothing().when(bookingRepository).deleteById(booking.getId());
        bookingService.delete(booking.getId());
    }

}