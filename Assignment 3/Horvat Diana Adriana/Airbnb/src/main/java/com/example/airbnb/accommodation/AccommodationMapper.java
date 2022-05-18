package com.example.airbnb.accommodation;

import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.accommodation.model.dto.AccommodationDTO;
import com.example.airbnb.booking.model.Booking;
import com.example.airbnb.booking.model.dto.BookingDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccommodationMapper {

    @Mappings({
            @Mapping(target = "address_city", ignore = true),
            @Mapping(target = "address_street", ignore = true),
            @Mapping(target = "imageURL", ignore = true),
            @Mapping(target = "amenities", ignore = true)
    })
    AccommodationDTO toDto(Accommodation accommodation);

    @Mappings({
            @Mapping(target = "address", ignore = true),
            @Mapping(target = "imageURL", ignore = true),
            @Mapping(target = "amenities", ignore = true),
            @Mapping(target = "user", ignore = true)
    })
    Accommodation fromDto(AccommodationDTO accommodationDTO);
//
//    @AfterMapping
//    default void populateAccommodationDTOAddressAndAmenities(AccommodationDTO accommodationDTO, @MappingTarget Accommodation accommodation){
//        accommodation.set
//        bookingDTO.setAccommodation_id(booking.getAccommodation().getId());
//    }
}
