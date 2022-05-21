package com.example.airbnb.booking;

import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.accommodation.model.dto.AccommodationDTO;
import com.example.airbnb.booking.model.Booking;
import com.example.airbnb.booking.model.dto.BookingDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")

public interface BookingMapper {
    @Mappings({
            @Mapping(target = "accommodation_id", ignore = true),
            @Mapping(target = "guest_id", ignore = true)
    })
    BookingDTO toDto(Booking booking);

    @Mappings({
            @Mapping(target = "accommodation", ignore = true),
            @Mapping(target = "guest", ignore = true)
    })
    Booking fromDto(BookingDTO bookingDTO);

    @AfterMapping
    default void populateBookingDTOAccommodationId(Booking booking, @MappingTarget BookingDTO bookingDTO){
        bookingDTO.setAccommodation_id(booking.getAccommodation().getId());
    }

    @AfterMapping
    default void populateBookingDTOGuestId(Booking booking, @MappingTarget BookingDTO bookingDTO){
        bookingDTO.setGuest_id(booking.getGuest().getId());
    }

}
