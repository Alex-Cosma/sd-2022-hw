package com.travel.BookingHotel.model.dto;
import com.travel.hotel.model.Hotel;
import com.travel.hotel.model.dto.HotelDTO;
import com.travel.user.dto.UserDTO;
import com.travel.user.model.User;
import lombok.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BookingHotelDTO {
    private Long id;
    private Set<String> userNames;
    private String hotelName;
    private Date startDate;
    private Date endDate;
    private Integer places;
    private Long price;
}
