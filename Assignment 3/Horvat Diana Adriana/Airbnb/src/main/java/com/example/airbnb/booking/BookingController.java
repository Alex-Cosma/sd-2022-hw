package com.example.airbnb.booking;

import com.example.airbnb.accommodation.AccommodationService;
import com.example.airbnb.booking.model.Booking;
import com.example.airbnb.booking.model.dto.BookingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.airbnb.user.UrlMapping.BOOKINGS;
import static com.example.airbnb.user.UrlMapping.ENTITY;

@RestController
@RequestMapping(BOOKINGS)
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @CrossOrigin
    @PostMapping()
    private BookingDTO create(@RequestBody BookingDTO bookingDTO) {

        return bookingService.save(bookingDTO);
    }
}
