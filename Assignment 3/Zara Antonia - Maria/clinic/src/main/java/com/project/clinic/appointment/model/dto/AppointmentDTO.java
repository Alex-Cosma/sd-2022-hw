package com.project.clinic.appointment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {
    private Long id;
    private Long dermatologistId;
    private Long customerId;
    private Long treatmentId;
    private String dermatologistUsername;
    private String treatmentTitle;
    private String date;
}
