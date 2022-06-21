package com.project.clinic.appointment;


import com.project.clinic.appointment.model.dto.AppointmentDTO;
import com.project.clinic.appointment.model.dto.AppointmentMinimalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.clinic.URLMapping.APPOINTMENT;
import static com.project.clinic.URLMapping.*;

@RestController
@RequestMapping(APPOINTMENT)
@RequiredArgsConstructor
@CrossOrigin(origins ="http://localhost:3000")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping(FIND_ALL + ID_PART)
    public List<AppointmentDTO> allAppointments(@PathVariable Long id) {
        List<AppointmentDTO>  appointmentDTOS = appointmentService.findAll(id);
        return appointmentDTOS;
    }

    @PostMapping(ADD + ID_PART)
    public AppointmentDTO create(@RequestBody AppointmentDTO appointment, @PathVariable Long id) {
        appointment.setCustomerId(id);
        return appointmentService.create(appointment);
    }

    @PostMapping(DELETE + ID_PART)
    public void delete(@PathVariable Long id){
        appointmentService.delete(id);
    }

    @GetMapping(FIND + ID_PART)
    public AppointmentDTO findAppointment(@PathVariable Long id){
        return appointmentService.findDTOById(id);
    }

}
