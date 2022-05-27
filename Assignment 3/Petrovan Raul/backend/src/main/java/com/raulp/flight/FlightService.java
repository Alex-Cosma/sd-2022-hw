package com.raulp.flight;

import com.raulp.flight.dtos.AirportDTO;
import com.raulp.flight.dtos.FlightDTO;
import com.raulp.flight.dtos.mappers.AirportMapper;
import com.raulp.flight.dtos.mappers.FlightMapper;
import com.raulp.flight.dtos.PlaneDTO;
import com.raulp.flight.dtos.mappers.PlaneMapper;
import com.raulp.flight.repos.AirportRepository;
import com.raulp.flight.repos.FlightRepository;
import com.raulp.flight.repos.PlaneRepository;
import com.raulp.report.ReportServiceFactory;
import com.raulp.report.ReportType;
import com.raulp.user.model.Instructor;
import com.raulp.user.model.Student;
import com.raulp.user.model.User;
import com.raulp.user.repos.InstructorRepository;
import com.raulp.user.repos.StudentRepository;
import com.raulp.user.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;
    private final PlaneRepository planeRepository;
    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository;

    private final ReportServiceFactory reportServiceFactory;

    private final FlightMapper flightMapper;
    private final PlaneMapper planeMapper;
    private final AirportMapper airportMapper;

    public List<FlightDTO> getFlightsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user instanceof Student) {
            Student student = (Student) user;
            return student.getFlights().stream().map(flightMapper::flightToFlightDTO)
                    .collect(Collectors.toList());
        }
        if (user instanceof Instructor) {
            Instructor instructor = (Instructor) user;
            return instructor.getFlights().stream().map(flightMapper::flightToFlightDTO)
                    .collect(Collectors.toList());
        }
        throw new RuntimeException("User is not a student or instructor");
    }

    public void create(FlightDTO flightDTO) {
        Flight flight = Flight.builder()
                .departureAirport(
                        airportRepository.findTopByNameLikeOrIcaoLike(flightDTO.getDepartureAirport().getName(),
                                        flightDTO.getDepartureAirport().getIcao())
                                .orElseThrow(() -> new RuntimeException("Airport not found")))
                .arrivalAirport(airportRepository.findTopByNameLikeOrIcaoLike(flightDTO.getArrivalAirport().getName()
                                , flightDTO.getDepartureAirport().getIcao())
                        .orElseThrow(() -> new RuntimeException("Airport not found")))
                .airplane(planeRepository.findById(flightDTO.getAirplane().getId())
                        .orElseThrow(() -> new RuntimeException("Plane not found")))
                .departureTime(flightDTO.getDepartureTime())
                .arrivalTime(flightDTO.getArrivalTime())
                .instructor(instructorRepository.findById(flightDTO.getInstructor().getId())
                        .orElseThrow(() -> new RuntimeException("Instructor not found")))
                .student(studentRepository.findById(flightDTO.getStudent().getId())
                        .orElseThrow(() -> new RuntimeException("Student not found")))
                .build();

        flightRepository.save(flight);
    }

    public List<FlightDTO> getFlightsForInstructorStudent(Long instructorId, Long studentId) {
        User user = userRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Instructor instructor = (Instructor) user;
        return instructor.getFlights().stream()
                .filter(flight -> flight.getStudent().getId().equals(studentId))
                .map(flightMapper::flightToFlightDTO).collect(
                        Collectors.toList());
    }

    public String export(ReportType type, Long userId) {
        try {
            return reportServiceFactory.getReportService(type).export(getFlightsForUser(userId));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PlaneDTO> getAirplanes() {
        return planeRepository.findAll().stream().map(planeMapper::planetoPlaneDTO).collect(
                Collectors.toList());
    }

    public List<AirportDTO> getAirports() {
        return airportRepository.findAll().stream().map(airportMapper::airportToAirportDTO).collect(
                Collectors.toList());
    }
}
