package com.travel.BookingFlight;

import com.travel.BookingFlight.model.BookingFlight;
import com.travel.BookingFlight.model.dto.BookingFlightDTO;
import com.travel.flight.FlightRepository;
import com.travel.flight.model.Flight;
import com.travel.user.UserRepository;
import static com.travel.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;

import com.travel.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;
@SpringBootTest
public class BookingFlightServiceIntegrationTest {
    @Autowired
    private  BookingFlightMapper bookingFlightMapper;

    @Autowired
    private BookingFlightService bookingFlightService;

    @Autowired
    private BookingFlightRepository bookingFlightRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        flightRepository.deleteAll();
        bookingFlightRepository.deleteAll();
        userRepository.deleteAll();
    }
    @Test
    void findAll(){
        Flight flight = Flight.builder().seats(100).id(1L).price(10).build();
        flightRepository.save(flight);
        User user = User.builder().id(1l).username("username").password("password").email(randomEmail()).build();
        userRepository.save(user);
        List<BookingFlight> bookings = listOf(BookingFlight.class);
        bookings.forEach(bookingFlight -> bookingFlight.setFlight(flight));
        bookings.forEach(bookingFlight -> bookingFlight.setUsers(Set.of(user)));
        bookings.forEach(bookingFlight -> bookingFlight.setSeats(100));
        bookingFlightRepository.saveAll(bookings);
        List<BookingFlightDTO> all = bookingFlightService.findAll();
        assertEquals(all.size(),bookings.size());
    }
}
