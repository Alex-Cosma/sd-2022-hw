package com.travel.flight;

import com.travel.flight.model.Flight;
import com.travel.flight.model.Flight.FlightBuilder;
import com.travel.flight.model.dto.FlightDTO;
import com.travel.flight.model.dto.FlightDTO.FlightDTOBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-27T09:40:25+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.8.jar, environment: Java 15 (Oracle Corporation)"
)
@Component
public class FlightMapperImpl implements FlightMapper {

    @Override
    public FlightDTO toDto(Flight flight) {
        if ( flight == null ) {
            return null;
        }

        FlightDTOBuilder flightDTO = FlightDTO.builder();

        flightDTO.id( flight.getId() );
        flightDTO.seats( flight.getSeats() );
        flightDTO.price( flight.getPrice() );

        return flightDTO.build();
    }

    @Override
    public Flight fromDto(FlightDTO flightDTO) {
        if ( flightDTO == null ) {
            return null;
        }

        FlightBuilder flight = Flight.builder();

        flight.id( flightDTO.getId() );
        flight.seats( flightDTO.getSeats() );
        flight.price( flightDTO.getPrice() );

        return flight.build();
    }
}
