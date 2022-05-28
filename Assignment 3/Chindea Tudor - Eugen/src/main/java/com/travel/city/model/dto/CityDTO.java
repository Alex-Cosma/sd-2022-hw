package com.travel.city.model.dto;
import com.travel.flight.model.dto.FlightDTO;
import com.travel.hotel.model.dto.HotelDTO;
import lombok.*;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CityDTO {
    private Long id;
    private String name;
    private Set<String> hotelListNames;
    private Set<Long> flightDeparturesIds;
    private Set<Long> flightArrivalsIds;
}
