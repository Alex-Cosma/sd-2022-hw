package com.project.clinic.appointment;

import com.project.clinic.TestCreationFactory;
import com.project.clinic.appointment.model.Appointment;
import com.project.clinic.appointment.model.dto.AppointmentDTO;
import com.project.clinic.treatment.TreatmentRepository;
import com.project.clinic.treatment.model.Treatment;
import com.project.clinic.user.RoleRepository;
import com.project.clinic.skin_color.SkinColorRepository;
import com.project.clinic.user.UserRepository;
import com.project.clinic.user.model.ERole;
import com.project.clinic.user.model.Role;
import com.project.clinic.skin_color.model.SkinColor;
import com.project.clinic.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class AppointmentServiceIntegrationTest {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SkinColorRepository skinColorRepository;

    @Autowired
    private TreatmentRepository treatmentRepository;

    private User professional;
    private User customer;
    private Treatment treatment;

    @BeforeEach
    void setUp(){
        appointmentRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        skinColorRepository.deleteAll();
        treatmentRepository.deleteAll();

        SkinColor skinColor = TestCreationFactory.newSkinColor();
        skinColor = skinColorRepository.save(skinColor);

        Role professionalRole = TestCreationFactory.newRole();
        professionalRole.setName(ERole.valueOf("PROFESSIONAL"));
        professionalRole = roleRepository.save(professionalRole);

        Role customerRole = TestCreationFactory.newRole();
        customerRole.setName(ERole.valueOf("CUSTOMER"));
        customerRole = roleRepository.save(customerRole);

        treatment = TestCreationFactory.newTreatment();
        treatment = treatmentRepository.save(treatment);

        customer = TestCreationFactory.newUser();
        customer.setRoles(Set.of(customerRole));
        customer.setSkinColor(skinColor);

        professional = TestCreationFactory.newUser();
        professional.setSkinColor(skinColor);
        professional.setRoles(Set.of(professionalRole));

        customer = userRepository.save(customer);
        professional = userRepository.save(professional);
    }

    @Test
    void findById(){
        Appointment appointment = TestCreationFactory.newAppointment();
        appointment.setCustomer(customer);
        appointment.setDermatologist(professional);
        appointment.setTreatment(treatment);
        Appointment saved = appointmentRepository.save(appointment);

        Appointment found = appointmentService.findById(saved.getId());

        Assertions.assertEquals(saved.getDate(), found.getDate());
        Assertions.assertEquals(saved.getCustomer().getId(), found.getCustomer().getId());
        Assertions.assertEquals(saved.getDermatologist().getId(), found.getDermatologist().getId());
        Assertions.assertEquals(saved.getTreatment().getId(), found.getTreatment().getId());
    }

    @Test
    void findDTOById(){
        Appointment appointment = TestCreationFactory.newAppointment();
        appointment.setCustomer(customer);
        appointment.setDermatologist(professional);
        appointment.setTreatment(treatment);
        Appointment saved = appointmentRepository.save(appointment);

        AppointmentDTO appointmentDTO = appointmentMapper.toDto(saved);

        AppointmentDTO found = appointmentService.findDTOById(saved.getId());

        Assertions.assertEquals(appointmentDTO, found);
    }
    
    @Test
    void findAll(){
        List<Appointment> appointments = TestCreationFactory.listOf(Appointment.class);
        for(Appointment a: appointments){
            a.setCustomer(customer);
            a.setDermatologist(professional);
            a.setTreatment(treatment);
        }
        appointmentRepository.saveAll(appointments);

        List<AppointmentDTO> all = appointmentService.findAll();

        Assertions.assertEquals(appointments.size(), all.size());
    }

    @Test
    void findAllById(){
        List<Appointment> appointments = TestCreationFactory.listOf(Appointment.class);
        for(Appointment a: appointments){
            a.setCustomer(customer);
            a.setDermatologist(professional);
            a.setTreatment(treatment);
        }
        appointmentRepository.saveAll(appointments);

        List<AppointmentDTO> allCustomer = appointmentService.findAll(customer.getId());
        List<AppointmentDTO> allDerm = appointmentService.findAll(professional.getId());
        System.out.println(allCustomer.size());
        System.out.println(allDerm.size());

        Assertions.assertEquals(appointments.size(), allCustomer.size());
        Assertions.assertEquals(appointments.size(), allDerm.size());
    }

    @Test
    void create(){
        Appointment appointment = TestCreationFactory.newAppointment();
        appointment.setCustomer(customer);
        appointment.setDermatologist(professional);
        appointment.setTreatment(treatment);
        AppointmentDTO dto = appointmentMapper.toDto(appointment);
        dto.setDate(appointment.getDate().toLocalDate().toString());

        AppointmentDTO saved = appointmentService.create(dto);

        Optional<Appointment> found = appointmentRepository.findById(saved.getId());
        Assertions.assertTrue(found.isPresent());
    }

    @Test
    void delete(){
        Appointment appointment = TestCreationFactory.newAppointment();
        appointment.setCustomer(customer);
        appointment.setDermatologist(professional);
        appointment.setTreatment(treatment);
        AppointmentDTO dto = appointmentMapper.toDto(appointment);
        dto.setDate(appointment.getDate().toLocalDate().toString());

        AppointmentDTO saved = appointmentService.create(dto);

        List<Appointment> appointments = appointmentRepository.findAll();
        Assertions.assertEquals(1, appointments.size());

        appointmentService.delete(saved.getId());

        appointments = appointmentRepository.findAll();
        Assertions.assertEquals(0, appointments.size());
    }
}
