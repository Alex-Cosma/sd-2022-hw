package com.travel.BookingFlight;

import com.travel.BookingFlight.model.BookingFlight;
import com.travel.BookingFlight.model.dto.BookingFlightDTO;
import org.mapstruct.*;

import java.util.stream.Collectors;
@Mapper(componentModel = "spring")
public interface BookingFlightMapper {
    @Mappings({
            @Mapping(target = "flightId", ignore = true),
            @Mapping(target = "seats", ignore = true),
            @Mapping(target = "userNames", ignore = true)
    })
    BookingFlightDTO toDto(BookingFlight bookingFlight);

    @Mappings({
            @Mapping(target = "flight", ignore = true),
            @Mapping(target = "seats", ignore = true),
            @Mapping(target = "users", ignore = true)
    })
    BookingFlight fromDto(BookingFlightDTO bookingFlightDto);

    @AfterMapping
    default void populateUsers(BookingFlight bookingFlight, @MappingTarget BookingFlightDTO bookingFlightDTO){
        bookingFlightDTO.setUserNames(bookingFlight.getUsers().stream().map(user -> user.getUsername()).collect(Collectors.toSet()));
    }

    @AfterMapping
    default void populateFlights(BookingFlight bookingFlight, @MappingTarget BookingFlightDTO bookingFlightDTO){
        bookingFlightDTO.setFlightId(bookingFlight.getFlight().getId());
    }
    @AfterMapping
    default void populateSeats(BookingFlight bookingFlight, @MappingTarget BookingFlightDTO bookingFlightDTO){
        bookingFlightDTO.setSeats(bookingFlight.getSeats());
    }
}
