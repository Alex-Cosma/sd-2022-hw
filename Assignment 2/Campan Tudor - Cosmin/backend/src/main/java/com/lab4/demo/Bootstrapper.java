package com.lab4.demo;

import com.lab4.demo.item.ItemRepository;
import com.lab4.demo.item.ItemService;
import com.lab4.demo.item.model.Item;
import com.lab4.demo.report.CSVReportService;
import com.lab4.demo.report.PdfReportService;
import com.lab4.demo.report.ReportType;
import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.SignupRequest;
import com.lab4.demo.user.RoleRepository;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.UserService;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final ItemRepository itemRepository;

    private final UserService userService;

    private final ItemService itemService;

    private final PdfReportService pdfReportService;

    private final CSVReportService csvReportService;
    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            itemRepository.deleteAll();
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
                    .email("alex@email.com")
                    .username("alex")
                    .password("WooHoo1!")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("alex1@email.com")
                    .username("alex1")
                    .password("WooHoo1!")
                    .roles(Set.of("CUSTOMER"))
                    .build());
        }
        UserDTO u = new UserDTO(Integer.toUnsignedLong(15),"postmalone","postmalone@gmail.com","malonepost",null);
        itemRepository.save(Item.builder().title("Capra cu 3 iezi").author("Ion Creanga").genre("Povesti").price(17).quantity(20).build());
        userService.newUser(u);
        itemRepository.save(Item.builder().title("Aiacuzero").author("Ion Barbu").genre("Povesti").price(17).quantity(0).build());
        //pdfReportService.exportP(itemService.findItems0());
        //csvReportService.exportC(itemService.findItems0());
    }
}
