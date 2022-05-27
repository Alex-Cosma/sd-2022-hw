package com.travel.hotel.model.dto;

import com.travel.city.model.City;
import com.travel.city.model.dto.CityDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {
    private Long id;
    private String name;
    private String cityName;
    private Integer places;
    private Integer price;
}
