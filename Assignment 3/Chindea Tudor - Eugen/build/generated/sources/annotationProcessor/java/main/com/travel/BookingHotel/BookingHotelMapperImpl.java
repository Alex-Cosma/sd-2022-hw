package com.travel.BookingHotel;

import com.travel.BookingHotel.model.BookingHotel;
import com.travel.BookingHotel.model.BookingHotel.BookingHotelBuilder;
import com.travel.BookingHotel.model.dto.BookingHotelDTO;
import com.travel.BookingHotel.model.dto.BookingHotelDTO.BookingHotelDTOBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-27T09:40:25+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.8.jar, environment: Java 15 (Oracle Corporation)"
)
@Component
public class BookingHotelMapperImpl implements BookingHotelMapper {

    @Override
    public BookingHotelDTO toDto(BookingHotel bookingHotel) {
        if ( bookingHotel == null ) {
            return null;
        }

        BookingHotelDTOBuilder bookingHotelDTO = BookingHotelDTO.builder();

        bookingHotelDTO.id( bookingHotel.getId() );
        bookingHotelDTO.startDate( bookingHotel.getStartDate() );
        bookingHotelDTO.endDate( bookingHotel.getEndDate() );
        bookingHotelDTO.price( bookingHotel.getPrice() );

        return bookingHotelDTO.build();
    }

    @Override
    public BookingHotel fromDto(BookingHotelDTO bookingHotelDto) {
        if ( bookingHotelDto == null ) {
            return null;
        }

        BookingHotelBuilder bookingHotel = BookingHotel.builder();

        bookingHotel.id( bookingHotelDto.getId() );
        bookingHotel.startDate( bookingHotelDto.getStartDate() );
        bookingHotel.endDate( bookingHotelDto.getEndDate() );
        bookingHotel.price( bookingHotelDto.getPrice() );

        return bookingHotel.build();
    }
}
