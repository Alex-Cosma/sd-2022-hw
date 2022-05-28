package com.travel.BookingFlight;

import com.travel.BookingFlight.model.BookingFlight;
import com.travel.BookingFlight.model.BookingFlight.BookingFlightBuilder;
import com.travel.BookingFlight.model.dto.BookingFlightDTO;
import com.travel.BookingFlight.model.dto.BookingFlightDTO.BookingFlightDTOBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-27T09:40:25+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.8.jar, environment: Java 15 (Oracle Corporation)"
)
@Component
public class BookingFlightMapperImpl implements BookingFlightMapper {

    @Override
    public BookingFlightDTO toDto(BookingFlight bookingFlight) {
        if ( bookingFlight == null ) {
            return null;
        }

        BookingFlightDTOBuilder bookingFlightDTO = BookingFlightDTO.builder();

        bookingFlightDTO.id( bookingFlight.getId() );
        bookingFlightDTO.date( bookingFlight.getDate() );

        return bookingFlightDTO.build();
    }

    @Override
    public BookingFlight fromDto(BookingFlightDTO bookingFlightDto) {
        if ( bookingFlightDto == null ) {
            return null;
        }

        BookingFlightBuilder bookingFlight = BookingFlight.builder();

        bookingFlight.id( bookingFlightDto.getId() );
        bookingFlight.date( bookingFlightDto.getDate() );

        return bookingFlight.build();
    }
}
