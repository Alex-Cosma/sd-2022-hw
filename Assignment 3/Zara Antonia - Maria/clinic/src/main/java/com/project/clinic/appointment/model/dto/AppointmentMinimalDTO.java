package com.project.clinic.appointment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentMinimalDTO {
    private Long dermatologistId;
    private Long customerId;
    private Long treatmentId;
}
