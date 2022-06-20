package com.project.clinic.appointment;

import com.project.clinic.appointment.model.Appointment;
import com.project.clinic.appointment.model.dto.AppointmentDTO;
import com.project.clinic.mail.EmailSender;
import com.project.clinic.mail.MailServer;
import com.project.clinic.treatment.TreatmentService;
import com.project.clinic.treatment.model.Treatment;
import com.project.clinic.user.UserService;
import com.project.clinic.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final UserService userService;
    private final TreatmentService treatmentService;
    private final MailServer mailServer;
    private final EmailSender emailSender;

    public Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found: " + id));
    }

    public AppointmentDTO findDTOById(Long id){
        return appointmentMapper.toDto(
                appointmentRepository.findById(id).get()
        );
    }


    public List<AppointmentDTO> findAll() {
        return appointmentRepository.findAll().stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> findAll(Long id) {
        User user = userService.findById(id);
        if(user.getRoles().stream().findFirst().get().getName().toString().equals("CUSTOMER"))
            return appointmentRepository.findAll().stream()
                    .filter(x -> x.getCustomer().getId().equals(id))
                    .map(appointmentMapper::toDto)
                    .collect(Collectors.toList());
        else
            return appointmentRepository.findAll().stream()
                    .filter(x -> x.getDermatologist().getId().equals(id))
                    .map(appointmentMapper::toDto)
                    .collect(Collectors.toList());
    }

    public AppointmentDTO create(AppointmentDTO appointment) {

        User customer = userService.findById(appointment.getCustomerId());
        User dermatologist = userService.findById(appointment.getDermatologistId());
        Treatment treatment = treatmentService.findById(appointment.getTreatmentId());

        appointment.setDermatologistUsername(dermatologist.getUsername());
        appointment.setTreatmentTitle(treatment.getTitle());

        emailSender.setEmailSender(mailServer.getJavaMailSender());
        emailSender.sendAppointmentEmail(customer.getUsername(), appointment);

        return appointmentMapper.toDto(appointmentRepository.save(
                appointmentMapper.fromDto(appointment).builder()
                .customer(customer)
                        .dermatologist(dermatologist)
                        .date(Date.valueOf(appointment.getDate()))
                        .treatment(treatment)
                        .build()
        ));
    }

    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }
}
