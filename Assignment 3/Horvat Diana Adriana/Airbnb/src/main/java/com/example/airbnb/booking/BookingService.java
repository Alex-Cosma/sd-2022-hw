package com.example.airbnb.booking;

import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.booking.model.Booking;
import com.example.airbnb.booking.model.dto.BookingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public BookingDTO save(BookingDTO booking) {
        return bookingMapper.toDto(bookingRepository.save(
                bookingMapper.fromDto(booking)
        ));
    }
}
