package com.travel.BookingFlight;

import com.travel.BookingFlight.model.BookingFlight;
import com.travel.BookingFlight.model.dto.BookingFlightDTO;
import com.travel.TestCreationFactory;
import com.travel.flight.FlightRepository;
import com.travel.flight.model.Flight;
import com.travel.user.UserRepository;
import com.travel.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jmx.export.annotation.ManagedAttribute;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static com.travel.TestCreationFactory.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class BookingFlightServiceTest {
    @InjectMocks
    BookingFlightService bookingFlightService;

    @Mock
    BookingFlightMapper bookingFlightMapper;

    @Mock
    BookingFlightRepository bookingFlightRepository;

    @Mock
    FlightRepository flightRepository;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookingFlightService = new BookingFlightService(bookingFlightMapper,bookingFlightRepository,flightRepository,userRepository);
    }

    @Test
    void findAll() {
        List<BookingFlight> bookings = TestCreationFactory.listOf(BookingFlight.class);
        when(bookingFlightRepository.findAll()).thenReturn(bookings);

        List<BookingFlightDTO> all = bookingFlightService.findAll();

        Assertions.assertEquals(bookings.size(), all.size());
    }

    @Test
    void bookFlight() {
        BookingFlight bookingFlight = newBookingFlight();
        BookingFlightDTO bookingFlightDTO = newBookingFlightDTO();

        Flight flight = newFlight();
        Long flightid = randomLong();
        flight.setId(flightid);

        String username = randomString();
        User user = newUser();
        user.setUsername(username);

        bookingFlight.setFlight(flight);
        bookingFlight.setUsers(Set.of(user));
        bookingFlightDTO.setUserNames(Set.of(username));
        bookingFlightDTO.setFlightId(flightid);
        when(bookingFlightMapper.fromDto(bookingFlightDTO)).thenReturn(bookingFlight);
        when(bookingFlightMapper.toDto(bookingFlight)).thenReturn(bookingFlightDTO);
        when(bookingFlightRepository.save(bookingFlight)).thenReturn(bookingFlight);
        when(flightRepository.findById(flightid)).thenReturn(Optional.of(flight));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        BookingFlightDTO newBookingDTO = bookingFlightService.bookFlight(bookingFlightDTO);
        assertEquals(newBookingDTO,bookingFlightDTO);
    }

    @Test
    void delete() {
        BookingFlight bookingFlight = newBookingFlight();
        when(bookingFlightRepository.save(bookingFlight)).thenReturn(bookingFlight);
        Flight flight = newFlight();
        when(flightRepository.save(flight)).thenReturn(flight);
        User user = newUser();
        when(userRepository.save(user)).thenReturn(user);
        bookingFlight.setFlight(flight);
        bookingFlight.setUsers(Set.of(user));
        Long id = bookingFlight.getId();
        when(bookingFlightRepository.findById(id)).thenReturn(Optional.of(bookingFlight));
        doNothing().when(bookingFlightRepository).delete(bookingFlight);
        bookingFlightService.delete(bookingFlight.getId());
        assertFalse(bookingFlightRepository.existsById(id));
    }
}