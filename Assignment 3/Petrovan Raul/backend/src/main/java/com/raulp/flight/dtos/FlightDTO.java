package com.raulp.flight.dtos;

import com.raulp.flight.Airport;
import com.raulp.flight.Plane;
import com.raulp.user.dto.instructor.InstructorMinimalDTO;
import com.raulp.user.dto.student.StudentMinimalDTO;
import com.raulp.user.model.Instructor;
import com.raulp.user.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {
    private Long id;
    private StudentMinimalDTO student;
    private InstructorMinimalDTO instructor;
    private PlaneDTO airplane;
    private AirportDTO departureAirport;
    private AirportDTO arrivalAirport;
    private Date departureTime;
    private Date arrivalTime;
}
