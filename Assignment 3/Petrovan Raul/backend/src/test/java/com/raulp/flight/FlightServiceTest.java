package com.raulp.flight;

import com.raulp.TestCreationFactory;
import com.raulp.flight.dtos.FlightDTO;
import com.raulp.flight.dtos.mappers.AirportMapper;
import com.raulp.flight.dtos.mappers.AirportMapperImpl;
import com.raulp.flight.dtos.mappers.FlightMapper;
import com.raulp.flight.dtos.mappers.FlightMapperImpl;
import com.raulp.flight.dtos.mappers.PlaneMapper;
import com.raulp.flight.dtos.mappers.PlaneMapperImpl;
import com.raulp.flight.repos.AirportRepository;
import com.raulp.flight.repos.FlightRepository;
import com.raulp.flight.repos.PlaneRepository;
import com.raulp.report.CSVReportService;
import com.raulp.report.ReportServiceFactory;
import com.raulp.report.ReportType;
import com.raulp.user.dto.instructor.InstructorMinimalDTO;
import com.raulp.user.dto.student.StudentMinimalDTO;
import com.raulp.user.mapper.InstructorMapperImpl;
import com.raulp.user.mapper.StudentMapper;
import com.raulp.user.mapper.StudentMapperImpl;
import com.raulp.user.model.Instructor;
import com.raulp.user.model.Student;
import com.raulp.user.repos.InstructorRepository;
import com.raulp.user.repos.StudentRepository;
import com.raulp.user.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FlightMapperImpl.class, AirportMapperImpl.class,
        PlaneMapperImpl.class, InstructorMapperImpl.class, StudentMapperImpl.class})
class FlightServiceTest {

    @InjectMocks
    private FlightService flightService;
    @Mock
    private FlightRepository flightRepository;
    @Mock
    private AirportRepository airportRepository;
    @Mock
    private PlaneRepository planeRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Autowired
    private FlightMapper flightMapper;
    @Autowired
    private PlaneMapper planeMapper;
    @Autowired
    private AirportMapper airportMapper;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        flightService = new FlightService(flightRepository, airportRepository, planeRepository,
                userRepository, instructorRepository, studentRepository, reportServiceFactory,
                flightMapper, planeMapper, airportMapper);
    }

    @Test
    void getFlightsForUser() {
        List<Flight> flights = TestCreationFactory.listOf(Flight.class);
        Set<Flight> flightSet = new HashSet<>(flights);
        Student student = TestCreationFactory.newStudent();
        student.setFlights(flightSet);
        Instructor instructor = TestCreationFactory.newInstructor();
        instructor.setFlights(flightSet);

        when(userRepository.findById(student.getId())).thenReturn(java.util.Optional.of(student));
        when(userRepository.findById(instructor.getId())).thenReturn(
                java.util.Optional.of(instructor));

        assertEquals(
                flights.stream().map(flightMapper::flightToFlightDTO).sorted(
                        new Comparator<FlightDTO>() {
                            @Override
                            public int compare(FlightDTO o1, FlightDTO o2) {
                                return o1.getId().compareTo(o2.getId());
                            }
                        }).collect(Collectors.toList()),
                flightService.getFlightsForUser(student.getId()).stream().sorted(
                        new Comparator<FlightDTO>() {
                            @Override
                            public int compare(FlightDTO o1, FlightDTO o2) {
                                return o1.getId().compareTo(o2.getId());
                            }
                        }).collect(Collectors.toList()));
        assertEquals(
                flights.stream().map(flightMapper::flightToFlightDTO).sorted(
                        new Comparator<FlightDTO>() {
                            @Override
                            public int compare(FlightDTO o1, FlightDTO o2) {
                                return o1.getId().compareTo(o2.getId());
                            }
                        }).collect(Collectors.toList()),

                flightService.getFlightsForUser(instructor.getId()).stream().sorted(
                        new Comparator<FlightDTO>() {
                            @Override
                            public int compare(FlightDTO o1, FlightDTO o2) {
                                return o1.getId().compareTo(o2.getId());
                            }
                        }

                ).collect(Collectors.toList()));
    }

    @Test
    void create() {
        FlightDTO flightDTO = TestCreationFactory.newFlightDTO();
        Airport airport = TestCreationFactory.newAirport();
        Plane plane = TestCreationFactory.newPlane();
        Instructor instructor = TestCreationFactory.newInstructor();
        Student student = TestCreationFactory.newStudent();

        flightDTO.setArrivalAirport(airportMapper.airportToAirportDTO(airport));
        flightDTO.setDepartureAirport(airportMapper.airportToAirportDTO(airport));
        flightDTO.setAirplane(planeMapper.planetoPlaneDTO(plane));
        flightDTO.setStudent(StudentMinimalDTO.builder().id(student.getId()).build());
        flightDTO.setInstructor(InstructorMinimalDTO.builder().id(instructor.getId()).build());

        when(airportRepository.findTopByNameLikeOrIcaoLike(
                flightDTO.getDepartureAirport().getName(),
                flightDTO.getDepartureAirport().getIcao())).thenReturn(Optional.of(airport));
        when(airportRepository.findTopByNameLikeOrIcaoLike(flightDTO.getArrivalAirport().getName(),
                flightDTO.getArrivalAirport().getIcao())).thenReturn(Optional.of(airport));
        when(planeRepository.findById(flightDTO.getAirplane().getId())).thenReturn(Optional.of(
                TestCreationFactory.newPlane()));
        when(instructorRepository.findById(flightDTO.getInstructor().getId())).thenReturn(
                Optional.of(
                        instructor));
        when(studentRepository.findById(flightDTO.getStudent().getId())).thenReturn(Optional.of(
                student));

        assertDoesNotThrow(() -> flightService.create(flightDTO));
    }

    @Test
    void getFlightsForInstructorStudent() {
        List<Flight> flights = TestCreationFactory.listOf(Flight.class);
        Set<Flight> flightSet = new HashSet<>(flights);
        Student student = TestCreationFactory.newStudent();
        Instructor instructor = TestCreationFactory.newInstructor();

        flightSet.stream().map(flight -> {
            flight.setInstructor(instructor);
            flight.setStudent(student);
            return flight;
        }).collect(Collectors.toList());
        student.setFlights(flightSet);
        instructor.setFlights(flightSet);
        when(userRepository.findById(student.getId())).thenReturn(java.util.Optional.of(student));
        when(userRepository.findById(instructor.getId())).thenReturn(
                java.util.Optional.of(instructor));

        assertEquals(
                flights.stream().map(flightMapper::flightToFlightDTO).collect(Collectors.toList()),
                flightService.getFlightsForInstructorStudent(instructor.getId(), student.getId()));
    }

    @Test
    void export() {
        List<Flight> flights = TestCreationFactory.listOf(Flight.class);
        Set<Flight> flightSet = new HashSet<>(flights);
        Student student = TestCreationFactory.newStudent();
        Instructor instructor = TestCreationFactory.newInstructor();
        flightSet.stream().map(flight -> {
            flight.setInstructor(instructor);
            flight.setStudent(student);
            return flight;
        }).collect(Collectors.toList());
        student.setFlights(flightSet);
        instructor.setFlights(flightSet);

        when(userRepository.findById(student.getId())).thenReturn(java.util.Optional.of(student));
        when(userRepository.findById(instructor.getId())).thenReturn(
                java.util.Optional.of(instructor));


        when(reportServiceFactory.getReportService(ReportType.CSV)).thenReturn(
                new CSVReportService());

        assertDoesNotThrow(() -> flightService.export(ReportType.CSV, student.getId()));
    }

    @Test
    void getAirplanes() {
        List<Plane> planes = TestCreationFactory.listOf(Plane.class);
        when(planeRepository.findAll()).thenReturn(planes);
        assertEquals(planes.stream().map(planeMapper::planetoPlaneDTO).collect(Collectors.toList()),
                flightService.getAirplanes());
    }

    @Test
    void getAirports() {
        List<Airport> airports = TestCreationFactory.listOf(Airport.class);
        when(airportRepository.findAll()).thenReturn(airports);
        assertEquals(airports.stream().map(airportMapper::airportToAirportDTO)
                        .collect(Collectors.toList()),
                flightService.getAirports());
    }
}