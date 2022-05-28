package com.policeSection.driver;

import com.policeSection.Mail.MailSendigService;
import com.policeSection.Reports.Model.Report;
import com.policeSection.Reports.ReportService;
import com.policeSection.Reports.ReportsRepository;
import com.policeSection.driver.model.Driver;
import com.policeSection.driver.model.dto.DriverDTO;
import com.policeSection.fine.FineMapper;
import com.policeSection.fine.FineService;
import com.policeSection.fine.Model.Fine;
import com.policeSection.fine.Model.dto.FineDTO;
import com.policeSection.section.SectionRepository;
import com.policeSection.section.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    private final SectionService sectionService;

    private final ReportService reportsService;

    private final MailSendigService mailSendigService;

    private final FineService fineService;

    public Driver findById(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
    }

    public List<DriverDTO> findAll() {
        return driverRepository.findAll().stream()
                .map(driverMapper::toDto)
                .collect(Collectors.toList());
    }

    public DriverDTO create(DriverDTO driver) {
        return driverMapper.toDto(driverRepository.save(
                driverMapper.fromDto(driver)
        ));
    }

    public DriverDTO edit(Long id, DriverDTO item) {
        Driver actDriver = findById(id);
        actDriver.setName(item.getName());
        actDriver.setEmail(item.getEmail());
        actDriver.setSectionNumber(item.getSectionNumber());
        return driverMapper.toDto(
                driverRepository.save(actDriver)
        );
    }

    public void delete(Long id) {
        Driver actDriver = findById(id);
        driverRepository.delete(actDriver);

    }

//    public List<FineDTO> findFinesForDriver(Long id) {
//        Driver actDriver = findById(id);
//        return actDriver.getFines().stream().map(fineMapper::toDto).collect(Collectors.toList());
//    }

    public DriverDTO addFine(Long id, String type) {
        Driver actDriver = findById(id);
        actDriver.getFines().add(fineService.findByType(type));
        driverRepository.save(actDriver);
        return driverMapper.toDto(actDriver);

    }


    public DriverDTO removeFine(Long id, String type) {
        Driver actDriver = findById(id);
        Fine f=fineService.findByType(type);
        actDriver.getFines().remove(f);
        driverRepository.save(actDriver);
        Report rep = Report.builder()
                .sectionNumber(actDriver.getSectionNumber())
                .description("Soferul " + actDriver.getName() + " a platit amenda de " + f.getPrice())
                .build();
        reportsService.create(rep);
        JavaMailSender javaMailSender = mailSendigService.getJavaMailSender();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        message.setTo(sectionService.findByNumber(actDriver.getSectionNumber()).getEmail());
        message.setSubject("Test");
        message.setText("Soferul " + actDriver.getName() + " a platit amenda de " + f.getPrice());
        javaMailSender.send(message);
        return driverMapper.toDto(actDriver);
    }


}
