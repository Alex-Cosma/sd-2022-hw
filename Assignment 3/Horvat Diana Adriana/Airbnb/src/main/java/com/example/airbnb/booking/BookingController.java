package com.example.airbnb.booking;

import com.example.airbnb.accommodation.AccommodationService;
import com.example.airbnb.booking.model.Booking;
import com.example.airbnb.booking.model.dto.BookingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.airbnb.user.UrlMapping.BOOKINGS;
import static com.example.airbnb.user.UrlMapping.ENTITY;

@RestController
//@RequestMapping(BOOKINGS)
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @CrossOrigin
    @PostMapping("/api/accommodations/{accommodation_id}/bookings")
    private BookingDTO create(@PathVariable(value = "accommodation_id") Long accommodation_id,
            @RequestBody BookingDTO bookingDTO) {
        bookingDTO.setAccommodation_id(accommodation_id);
//        BookingDTO bookingDTO = accommodationService.findById(accommodation_id).map(accommodation -> {
//            bookingDTORequest.setAccommodation_id(tutorial);
//            return commentRepository.save(commentRequest);
//        }).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));
        System.out.println(bookingDTO);
        return bookingService.save(bookingDTO);
    }
}
