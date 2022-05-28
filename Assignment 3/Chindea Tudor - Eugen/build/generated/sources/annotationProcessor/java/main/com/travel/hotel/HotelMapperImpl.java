package com.travel.hotel;

import com.travel.hotel.model.Hotel;
import com.travel.hotel.model.Hotel.HotelBuilder;
import com.travel.hotel.model.dto.HotelDTO;
import com.travel.hotel.model.dto.HotelDTO.HotelDTOBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-27T09:40:25+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.8.jar, environment: Java 15 (Oracle Corporation)"
)
@Component
public class HotelMapperImpl implements HotelMapper {

    @Override
    public HotelDTO toDto(Hotel hotel) {
        if ( hotel == null ) {
            return null;
        }

        HotelDTOBuilder hotelDTO = HotelDTO.builder();

        hotelDTO.id( hotel.getId() );
        hotelDTO.name( hotel.getName() );
        hotelDTO.places( hotel.getPlaces() );
        hotelDTO.price( hotel.getPrice() );

        return hotelDTO.build();
    }

    @Override
    public Hotel fromDto(HotelDTO hotelDto) {
        if ( hotelDto == null ) {
            return null;
        }

        HotelBuilder hotel = Hotel.builder();

        hotel.id( hotelDto.getId() );
        hotel.name( hotelDto.getName() );
        hotel.places( hotelDto.getPlaces() );
        hotel.price( hotelDto.getPrice() );

        return hotel.build();
    }
}
