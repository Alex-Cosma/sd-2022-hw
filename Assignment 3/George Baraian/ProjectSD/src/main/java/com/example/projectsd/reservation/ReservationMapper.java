package com.example.projectsd.reservation;

import com.example.projectsd.reservation.model.Reservation;
import com.example.projectsd.reservation.model.dto.ReservationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationDTO toDto(Reservation reservation);

    Reservation fromDto(ReservationDTO reservationDTO);

}
