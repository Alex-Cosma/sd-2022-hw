package com.example.airbnb.booking;

import com.example.airbnb.accommodation.AccommodationRepository;
import com.example.airbnb.accommodation.AccommodationService;
import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.booking.model.Booking;
import com.example.airbnb.booking.model.dto.BookingDTO;
import com.example.airbnb.user.UserService;
import com.example.airbnb.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final AccommodationService accommodationService;
    private final UserService userService;
    private final BookingMapper bookingMapper;

    public BookingDTO save(BookingDTO bookingDTO) {
        Booking booking = bookingMapper.fromDto(bookingDTO);
        Accommodation accommodation = accommodationService.findById(bookingDTO.getAccommodation_id());

        booking.setAccommodation(accommodation);

        User user = userService.findById(bookingDTO.getGuest_id());
        booking.setGuest(user);

        BookingDTO bookingDTO1 = bookingMapper.toDto(bookingRepository.save(booking));
        bookingMapper.populateBookingDTOAccommodationId(booking, bookingDTO1);
        bookingMapper.populateBookingDTOGuestId(booking, bookingDTO1);

        return bookingDTO1;
    }

    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }

}
