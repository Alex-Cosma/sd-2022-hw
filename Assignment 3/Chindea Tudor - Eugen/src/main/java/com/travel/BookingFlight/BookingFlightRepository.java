package com.travel.BookingFlight;

import com.travel.BookingFlight.model.BookingFlight;
import com.travel.flight.model.Flight;
import com.zaxxer.hikari.util.FastList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface BookingFlightRepository extends JpaRepository<BookingFlight, Long> {
    Optional<BookingFlight> findByDateAndFlight(Date date, Flight flight);
}
