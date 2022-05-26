package com.raulp.flight;

import com.raulp.UrlMapping;
import com.raulp.flight.dtos.AirportDTO;
import com.raulp.flight.dtos.FlightDTO;
import com.raulp.flight.dtos.PlaneDTO;
import com.raulp.report.ReportType;
import com.raulp.user.dto.student.StudentMinimalDTO;
import com.raulp.user.services.InstructorService;
import com.raulp.websocket.MessageDTO;
import com.raulp.websocket.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import static com.raulp.UrlMapping.BOOKS;
import static com.raulp.UrlMapping.EXPORT_REPORT;

@RestController
@RequestMapping(UrlMapping.API_PATH + UrlMapping.FLIGHTS)
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;
    private final InstructorService instructorService;
    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    public List<FlightDTO> getFlightsForUser(@PathVariable Long userId) {
        return flightService.getFlightsForUser(userId);
    }

    @PostMapping(UrlMapping.ADD_FLIGHT)
    public void addFlight(@RequestBody FlightDTO flightDTO) {
        flightService.create(flightDTO);

        notificationService.sendNotification(flightDTO.getStudent().getId(),
                flightDTO.getInstructor().getId());
    }

    @GetMapping(UrlMapping.GET_FLIGHT)
    public List<FlightDTO> getFlightsForInstructorStudent(@PathVariable Long instructorId,
                                                          @PathVariable Long studentId) {
        return flightService.getFlightsForInstructorStudent(instructorId, studentId);
    }

    @GetMapping(EXPORT_REPORT)
    public ResponseEntity<Resource> exportReport(@PathVariable String type,
                                                 @PathVariable Long userId) {
        ReportType reportType = ReportType.valueOf(type.toUpperCase());
        File file = new File(flightService.export(reportType, userId));
        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping(UrlMapping.UNASSIGN_STUDENT)
    public void unassignStudent(@RequestBody StudentMinimalDTO studentMinimalDTO) {
        instructorService.unassignStudent(studentMinimalDTO.getId());
    }

}
