package com.example.airbnb.booking;

import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.accommodation.model.dto.AccommodationDTO;
import com.example.airbnb.booking.model.Booking;
import com.example.airbnb.booking.model.dto.BookingDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface BookingMapper {
    BookingDTO toDto(Booking booking);

    Booking fromDto(BookingDTO bookingDTO);
}
