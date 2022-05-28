package com.travel.BookingFlight;

import com.travel.BookingFlight.model.BookingFlight;
import com.travel.flight.FlightRepository;
import com.travel.flight.model.Flight;
import com.travel.hotel.HotelRepository;
import com.travel.user.UserRepository;
import com.travel.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BookingFlightRepositoryTest {

    @Autowired
    private BookingFlightRepository repository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        userRepository.deleteAll();
        flightRepository.deleteAll();
        repository.deleteAll();
    }
    @Test
    void findByDateAndFlight() {
        Flight flight = flightRepository.save(Flight.builder().seats(100).price(100).build());
        User user = userRepository.save(User.builder().username("aaa").password("aeffdsfer").email("ameil").build());
        Date date = new Date();
        BookingFlight bookingFlight = repository.save(BookingFlight.builder().flight(flight).date(date).seats(100).build());
        assertNotNull(repository.findByDateAndFlight(date,flight));
    }
}