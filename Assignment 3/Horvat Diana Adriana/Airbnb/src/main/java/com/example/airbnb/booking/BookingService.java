package com.example.airbnb.booking;

import com.example.airbnb.accommodation.AccommodationRepository;
import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.booking.model.Booking;
import com.example.airbnb.booking.model.dto.BookingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final AccommodationRepository accommodationRepository;
    private final BookingMapper bookingMapper;

    public BookingDTO save(BookingDTO bookingDTO) {
        Booking booking = bookingMapper.fromDto(bookingDTO);
        Accommodation accommodation = accommodationRepository.findById(bookingDTO.getAccommodation_id())
                .orElseThrow(() -> new EntityNotFoundException("Accommodation not found: " + bookingDTO.getAccommodation_id()));

        booking.setAccommodation(accommodation);

        BookingDTO bookingDTO1 = bookingMapper.toDto(bookingRepository.save(booking));
        bookingMapper.populateBookingDTOAccommodationId(booking, bookingDTO1);

        return bookingDTO1;
    }
}
