package com.project.clinic.appointment;

import com.project.clinic.TestCreationFactory;
import com.project.clinic.appointment.model.Appointment;
import com.project.clinic.appointment.model.dto.AppointmentDTO;
import com.project.clinic.mail.EmailSender;
import com.project.clinic.mail.MailServer;
import com.project.clinic.treatment.TreatmentService;
import com.project.clinic.user.UserService;
import com.project.clinic.user.model.ERole;
import com.project.clinic.user.model.Role;
import com.project.clinic.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.fail;

@SpringBootTest
public class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @Mock
    private UserService userService;

    @Mock
    private TreatmentService treatmentService;

    @Mock
    private EmailSender emailSender;

    @Mock
    private MailServer mailServer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        appointmentService = new AppointmentService(appointmentRepository, appointmentMapper, userService, treatmentService, mailServer, emailSender);
    }

    @Test
    void findById(){
        Appointment appointment = TestCreationFactory.newAppointment();

        when(appointmentRepository.findById(appointment.getId())).thenReturn(java.util.Optional.of(appointment));

        Appointment found = appointmentService.findById(appointment.getId());

        Assertions.assertEquals(appointment.getCustomer().getUsername(), found.getCustomer().getUsername());
        Assertions.assertEquals(appointment.getDermatologist().getUsername(), found.getDermatologist().getUsername());
        Assertions.assertEquals(appointment.getDate().toString(), found.getDate().toString());
    }

    @Test
    void findDTOBtId(){
        Appointment appointment = TestCreationFactory.newAppointment();
        AppointmentDTO dto = AppointmentDTO.builder()
                .id(appointment.getId())
                .treatmentTitle(appointment.getTreatment().getTitle())
                .treatmentId(appointment.getTreatment().getId())
                .dermatologistUsername(appointment.getDermatologist().getUsername())
                .date(appointment.getDate().toString())
                .dermatologistId(appointment.getDermatologist().getId())
                .customerId(appointment.getCustomer().getId())
                .build();

        when(appointmentRepository.findById(appointment.getId())).thenReturn(java.util.Optional.of(appointment));
        when(appointmentMapper.toDto(appointment)).thenReturn(dto);

        AppointmentDTO found = appointmentService.findDTOById(appointment.getId());

        Assertions.assertEquals(appointment.getCustomer().getId(), found.getCustomerId());
        Assertions.assertEquals(appointment.getDermatologist().getUsername(), found.getDermatologistUsername());
        Assertions.assertEquals(appointment.getDate().toString(), found.getDate());
    }
    
    @Test
    void findAll(){
        List<Appointment> appointments = TestCreationFactory.listOf(Appointment.class);
        for(Appointment a: appointments){
            AppointmentDTO dto = AppointmentDTO.builder()
                    .id(a.getId())
                    .treatmentTitle(a.getTreatment().getTitle())
                    .treatmentId(a.getTreatment().getId())
                    .dermatologistUsername(a.getDermatologist().getUsername())
                    .date(a.getDate().toString())
                    .dermatologistId(a.getDermatologist().getId())
                    .customerId(a.getCustomer().getId())
                    .build();
            when(appointmentMapper.toDto(a)).thenReturn(dto);
        }

        when(appointmentRepository.findAll()).thenReturn(appointments);

        List<AppointmentDTO> all = appointmentService.findAll();

        Assertions.assertEquals(appointments.size(), all.size());
    }

    @Test
    void findAllById(){
        List<Appointment> appointments = TestCreationFactory.listOf(Appointment.class);


        User user = TestCreationFactory.newUser();
        user.setId(1L);

        User derm = TestCreationFactory.newUser();
        Role dermRole = TestCreationFactory.newRole();
        dermRole.setName(ERole.valueOf("PROFESSIONAL"));
        derm.setRoles(Collections.singleton(dermRole));
        derm.setId(3L);

        int dermCount = 0;
        int customerCount = 0;

        for(int i = 0; i < appointments.size(); i++){
            Appointment a = appointments.get(i);

            if(i % 2 == 0){
                User customer = a.getCustomer();
                customer.setId(2L);
                a.setCustomer(customer);

                User dermatologist = a.getDermatologist();
                dermatologist.setId(3L);
                a.setDermatologist(dermatologist);

                dermCount++;

            } else {
                User customer = a.getCustomer();
                customer.setId(1L);
                a.setCustomer(customer);

                User dermatologist = a.getDermatologist();
                dermatologist.setId(2L);
                a.setDermatologist(dermatologist);

                customerCount++;
            }

            AppointmentDTO dto = AppointmentDTO.builder()
                    .id(a.getId())
                    .treatmentTitle(a.getTreatment().getTitle())
                    .treatmentId(a.getTreatment().getId())
                    .dermatologistUsername(a.getDermatologist().getUsername())
                    .date(a.getDate().toString())
                    .dermatologistId(a.getDermatologist().getId())
                    .customerId(a.getCustomer().getId())
                    .build();
            when(appointmentMapper.toDto(a)).thenReturn(dto);
        }

        when(appointmentRepository.findAll()).thenReturn(appointments);
        when(userService.findById(1L)).thenReturn(user);
        when(userService.findById(3L)).thenReturn(derm);

        List<AppointmentDTO> allCustomerAppointments = appointmentService.findAll(1L);
        Assertions.assertEquals(customerCount, allCustomerAppointments.size());

        List<AppointmentDTO> allDermAppointments = appointmentService.findAll(3L);
        Assertions.assertEquals(dermCount, allDermAppointments.size());
    }

    @Test
    void create(){
        Appointment a = TestCreationFactory.newAppointment();

        User customer = a.getCustomer();
        customer.setId(2L);
        a.setCustomer(customer);

        User dermatologist = a.getDermatologist();
        dermatologist.setId(3L);
        a.setDermatologist(dermatologist);
        
        AppointmentDTO dto = AppointmentDTO.builder()
                .id(a.getId())
                .treatmentTitle(a.getTreatment().getTitle())
                .treatmentId(a.getTreatment().getId())
                .dermatologistUsername(a.getDermatologist().getUsername())
                .date(a.getDate().toString())
                .dermatologistId(a.getDermatologist().getId())
                .customerId(a.getCustomer().getId())
                .build();

        Appointment a2 = a;
        a2.setCustomer(customer);
        a2.setDermatologist(dermatologist);
        a2.setTreatment(a.getTreatment());
        a2.setId(null);
        
        when(appointmentMapper.toDto(a)).thenReturn(dto);
        when(appointmentMapper.fromDto(dto)).thenReturn(a);
        when(userService.findById(2L)).thenReturn(a.getCustomer());
        when(userService.findById(3L)).thenReturn(a.getDermatologist());
        when(treatmentService.findById(a.getTreatment().getId())).thenReturn(a.getTreatment());
        when(appointmentRepository.save(a2)).thenReturn(a);

        AppointmentDTO saved = appointmentService.create(dto);
        Assertions.assertEquals(dto.getCustomerId(), saved.getCustomerId());
        Assertions.assertEquals(dto.getDermatologistId(), saved.getDermatologistId());
        Assertions.assertEquals(dto.getDate(), saved.getDate());
    }

    @Test
    void delete(){
        Appointment appointment = TestCreationFactory.newAppointment();

        try {
            appointmentService.delete(appointment.getId());
        }
        catch (Exception e){
            fail("Should not have thrown exception");
        }
    }
}
