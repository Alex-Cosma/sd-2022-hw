package com.travel.BookingHotel;

import com.travel.BookingHotel.model.BookingHotel;
import com.travel.BookingHotel.model.dto.BookingHotelDTO;

import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookingHotelMapper {

    @Mappings({
            @Mapping(target = "hotelName", ignore = true),
            @Mapping(target = "places", ignore = true),
            @Mapping(target = "userNames", ignore = true)
    })
     BookingHotelDTO toDto(BookingHotel bookingHotel);

    @Mappings({
            @Mapping(target = "hotel", ignore = true),
            @Mapping(target = "places", ignore = true),
            @Mapping(target = "users", ignore = true)
    })
    BookingHotel fromDto(BookingHotelDTO bookingHotelDto);

    @AfterMapping
    default void populateHotels(BookingHotel bookingHotel, @MappingTarget BookingHotelDTO bookingHotelDTO){
        bookingHotelDTO.setHotelName(bookingHotel.getHotel().getName());
    }

    @AfterMapping
    default void populateUsers(BookingHotel bookingHotel, @MappingTarget BookingHotelDTO bookingHotelDTO){
        bookingHotelDTO.setUserNames(bookingHotel.getUsers().stream().map(user -> user.getUsername()).collect(Collectors.toSet()));
    }
    @AfterMapping
    default void populatePlaces(BookingHotel bookingHotel, @MappingTarget BookingHotelDTO bookingHotelDTO){
        bookingHotelDTO.setPlaces(bookingHotel.getPlaces());
    }
}
