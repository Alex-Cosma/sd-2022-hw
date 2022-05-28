package com.policeSection.driver;

import com.policeSection.Mail.MailSendigService;
import com.policeSection.Reports.ReportService;
import com.policeSection.driver.model.Driver;
import com.policeSection.driver.model.dto.DriverDTO;
import com.policeSection.fine.FineMapper;
import com.policeSection.fine.FineRepository;
import com.policeSection.fine.FineService;
import com.policeSection.fine.Model.Fine;
import com.policeSection.fine.Model.dto.FineDTO;
import com.policeSection.section.SectionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DriverServiceIntegrationTest {

    @Autowired
    private DriverService driverService;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private DriverMapper driverMapper;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private ReportService reportsService;
    @Autowired
    private MailSendigService mailSendigService;
    @Autowired
    private FineService fineService;

    @BeforeEach
    void setUp() {
        driverRepository.deleteAll();
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
        driverRepository.save(driver1);
        driverRepository.save(driver2);
        List<DriverDTO> allDrivers= driverService.findAll();
        assertEquals(list.size(),allDrivers.size());

    }
    @Test
    void create()
    {
        DriverDTO driver = DriverDTO.builder()
                .id(1L)
                .email("random")
                .name("random")
                .sectionNumber(3)
                .fines(new ArrayList<>())
                .build();

        DriverDTO createdDriver= driverService.create(driver);
        Assertions.assertEquals(createdDriver,driver);
    }
    @Test
    void edit()
    {


        DriverDTO driverDTO = DriverDTO.builder().id(1L)
                .email("random")
                .name("random")
                .sectionNumber(3)
                .fines(new ArrayList<>())
                .build();

        driverDTO=driverService.create(driverDTO);
        driverDTO.setName("random test");
        driverService.edit(driverDTO.getId(),driverDTO);
        Assertions.assertNotNull(driverRepository.findFirstByNameEquals("random test"));
    }
    @Test
    void findById()
    {
        Driver driver1 = Driver.builder().id(1000L)
                .email("random")
                .name("random")
                .sectionNumber(3)
                .fines(new ArrayList<>())
                .build();
        driver1=driverRepository.save(driver1);

        Driver driverFound=driverService.findById(driver1.getId());
        assertEquals(driverFound.getName(),driver1.getName());
    }
    @Test
    void delete()
    {
        Driver driver1 = Driver.builder().id(1000L)
                .email("random")
                .name("random")
                .sectionNumber(3)
                .fines(new ArrayList<>())
                .build();
        driver1=driverRepository.save(driver1);;
        driverService.delete(driver1.getId());
        Assertions.assertFalse(driverRepository.existsById(driver1.getId()));
    }
}
