package com.travel.flight.model.dto;

import com.travel.city.model.dto.CityDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {
    private Long id;
    private Integer seats;
    private Integer price;
    private String departureCityName;
    private String arrivalCityName;
}
