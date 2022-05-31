package com.example.projectsd.reservation.model.dto;

import com.example.projectsd.court.model.Court;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String username;
    private Court court;

    @Override
    public String toString() {
        return id + ", " + startTime + ", "
                + endTime +  ", "+ username + ", "
                + court.getName();
    }
}
