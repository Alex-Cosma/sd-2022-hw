package com.travel.BookingFlight.model.dto;

import com.travel.flight.model.dto.FlightDTO;
import com.travel.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BookingFlightDTO {
    private Long id;
    private Set<String> userNames;
    private Long flightId;
    private Integer seats;
    private Date date;

}

