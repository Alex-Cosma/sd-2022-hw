package com.policeSection.driver;

import com.policeSection.Mail.MailSendigService;
import com.policeSection.Reports.ReportService;
import com.policeSection.Reports.ReportsRepository;
import com.policeSection.driver.model.Driver;
import com.policeSection.driver.model.dto.DriverDTO;
import com.policeSection.fine.FineMapper;
import com.policeSection.fine.FineService;
import com.policeSection.fine.Model.Fine;
import com.policeSection.section.Model.Section;
import com.policeSection.section.SectionRepository;
import com.policeSection.section.SectionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class DriverServiceTest {
    @InjectMocks
    private DriverService driverService;
    @Mock
    private DriverRepository driverRepository;
    @Mock
    private DriverMapper driverMapper;
    @Mock
    private FineService fineService;
    @Mock
    private SectionService sectionService;
    @Mock
    private ReportService reportsService;
    @Mock
    private MailSendigService mailSendigService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        driverService = new DriverService(driverRepository, driverMapper, sectionService, reportsService, mailSendigService, fineService);
    }

    @Test
    void create() {
        DriverDTO driver = DriverDTO.builder()
                .id(1L)
                .email("random")
                .name("random")
                .sectionNumber(3)
                .fines(new ArrayList<>())
                .build();
        when(driverMapper.toDto(driverRepository.save(driverMapper.fromDto(driver)))).thenReturn(driver);
        DriverDTO createdDriver = driverService.create(driver);
        Assertions.assertEquals(createdDriver, driver);
    }

    @Test
    void edit() {
        Driver driver = Driver.builder().id(1L)
                .email("random")
                .name("random")
                .sectionNumber(3)
                .fines(new ArrayList<>())
                .build();
        DriverDTO driverDTO = DriverDTO.builder().id(1L)
                .email("random")
                .name("random")
                .sectionNumber(3)
                .fines(new ArrayList<>())
                .build();
        when(driverRepository.findById(1L)).thenReturn(Optional.ofNullable(driver)
        );
        when(driverMapper.toDto(driverRepository.save(driverMapper.fromDto(driverDTO)))).thenReturn(driverDTO);
        DriverDTO createdDriver = driverService.create(driverDTO);
        createdDriver.setName("random test");
        DriverDTO editedDriver = driverService.edit(createdDriver.getId(), createdDriver);
        Assertions.assertEquals("random test", editedDriver.getName());
    }

    @Test
    void findAll() {
        Driver driver1 = Driver.builder().id(1L)
                .email("random")
                .name("random")
                .sectionNumber(3)
                .fines(new ArrayList<>())
                .build();
        Driver driver2 = Driver.builder().id(2L)
                .email("random")
                .name("random")
                .sectionNumber(3)
                .fines(new ArrayList<>())
                .build();
        List<Driver> list = new ArrayList<>();
        list.add(driver1);
        list.add(driver2);
        when(driverRepository.findAll()).thenReturn(list);
        List<DriverDTO> allDrivers = driverService.findAll();
        assertEquals(list.size(), allDrivers.size());
    }

    @Test
    void delete() {
        Driver driver1 = Driver.builder().id(1L)
                .email("random")
                .name("random")
                .sectionNumber(3)
                .fines(new ArrayList<>())
                .build();
        when(driverRepository.save(driver1)).thenReturn(driver1);
        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver1));
        when(driverRepository.existsById(1L)).thenReturn(false);
        driverService.delete(1L);
        Assertions.assertFalse(driverRepository.existsById(1L));
    }

    @Test
    void addFine() {
        Driver driver1 = Driver.builder().id(1L)
                .email("random")
                .name("random")
                .sectionNumber(3)
                .fines(new ArrayList<>())
                .build();
        Fine fine = Fine.builder()
                .id(1L)
                .type("random")
                .description("random")
                .price(200)
                .build();
        when(driverRepository.save(driver1)).thenReturn(driver1);
        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver1));
        when(fineService.findByType("random")).thenReturn(fine);
        when(driverRepository.save(driver1)).thenReturn(driver1);
        when(driverMapper.fromDto(driverService.addFine(1L, "random"))).thenReturn(driver1);
        driver1 = driverMapper.fromDto(driverService.addFine(1L, "random"));

        Assertions.assertNotEquals(driver1.getFines().size(), 0);


    }

    @Test
     void findById()
    {
        Driver driver1 = Driver.builder().id(1L)
                .email("random")
                .name("random")
                .sectionNumber(3)
                .fines(new ArrayList<>())
                .build();
        when(driverRepository.findById(1L)).thenReturn(Optional.ofNullable(driver1));
        Driver driverFound=driverService.findById(1L);
        assertEquals(driverFound,driver1);
    }
//    @Test
//    void removeFine()
//    {
//        Driver driver1=Driver.builder().id(1L)
//                .email("random")
//                .name("random")
//                .sectionNumber(3)
//                .fines(new ArrayList<>())
//                .build();
//        Fine fine=Fine.builder()
//                .id(1L)
//                .type("random")
//                .description("random")
//                .price(200)
//                .build();
//        Section section=new Section();
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//
//        mailSender.setUsername("fromtest70@gmail.com");
//        mailSender.setPassword("fromtest%70TEST");
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
//        when(driverRepository.save(driver1)).thenReturn(driver1);
//        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver1));
//        when(fineService.findByType("random")).thenReturn(fine);
//        when(driverRepository.save(driver1)).thenReturn(driver1);
//        when(driverMapper.fromDto(driverService.addFine(1L,"random"))).thenReturn(driver1);
//        driver1=driverMapper.fromDto(driverService.addFine(1L,"random"));
//        when(sectionService.findByNumber(3)).thenReturn(section);
//        when(mailSendigService.getJavaMailSender()).thenReturn(mailSender);
//        driver1=driverMapper.fromDto(driverService.removeFine(driver1.getId(),"random"));
//
//        Assertions.assertNotEquals(driver1.getFines().size(),0);
//
//
//    }




}
