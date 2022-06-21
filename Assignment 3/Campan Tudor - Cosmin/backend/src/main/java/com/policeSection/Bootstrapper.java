package com.policeSection;

import com.policeSection.Mail.MailSendigService;
import com.policeSection.Reports.Model.Report;
import com.policeSection.Reports.ReportsRepository;
import com.policeSection.driver.DriverRepository;
import com.policeSection.driver.DriverService;
import com.policeSection.driver.model.Driver;
import com.policeSection.fine.FineRepository;
import com.policeSection.fine.Model.Fine;
import com.policeSection.section.Model.Section;
import com.policeSection.section.SectionRepository;
import com.policeSection.security.AuthService;
import com.policeSection.security.dto.SignupRequest;
import com.policeSection.user.RoleRepository;
import com.policeSection.user.UserRepository;
import com.policeSection.user.model.ERole;
import com.policeSection.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final SectionRepository sectionRepository;
    private final FineRepository fineRepository;
    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final ReportsRepository reportsRepository;

    private final DriverRepository driverRepository;

    private final DriverService driverService;

    @Value("false")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        if (bootstrap) {
            driverRepository.deleteAll();
            sectionRepository.deleteAll();
            fineRepository.deleteAll();
            Report report1= Report.builder()
                    .sectionNumber(3)
                    .description("Soferul Alex a platit amenda de 300 de lei")
                    .build();
            reportsRepository.save(report1);
            Section section = Section.builder()
                    .email("sectia3rendoszeg@gmail.com")
                    .number(3)
                    .build();
            sectionRepository.save(section);
            Fine fine=Fine.builder()
                    .type("Speeding")
                    .description("Over 30 km/h")
                    .price(200)
                    .build();
            fineRepository.save(fine);

            Fine fine2=Fine.builder()
                    .type("Speeding2")
                    .description("Over 30 km/h")
                    .price(300)
                    .build();
            fineRepository.save(fine2);

            Driver driver= Driver.builder()
                    .name("Valentin Suciu")
                    .sectionNumber(4)
                    .email("valisuciu@gmail.com")
                    .fines(new ArrayList<>())
                    .build();
            Section itemSaved = sectionRepository.save(Section.builder().number(4)
                    .email("tudorcampan@gmail.com")
                    .build());
            sectionRepository.save(itemSaved);
            driverRepository.save(driver);
            driverService.addFine(driverRepository.findFirstByNameEquals("Valentin Suciu").getId(),"Speeding");
            driverService.addFine(driverRepository.findFirstByNameEquals("Valentin Suciu").getId(),"Speeding");
            driverService.removeFine(driverRepository.findFirstByNameEquals("Valentin Suciu").getId(),"Speeding");

            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("tudor@email.com")
                    .username("tudor")
                    .password("WooHoo1!")
                    .roles(Set.of("POLICEMAN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("tudor1@email.com")
                    .username("tudor1")
                    .password("WooHoo1!")
                    .roles(Set.of("EMPLOYEE"))
                    .build());
        }
    }
}
