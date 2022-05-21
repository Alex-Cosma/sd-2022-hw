package com.example.airbnb.accommodation;

import com.example.airbnb.TestCreationFactory;
import com.example.airbnb.accommodation.model.Accommodation;
import com.example.airbnb.accommodation.model.dto.AccommodationDTO;
import com.example.airbnb.user.UserService;
import com.example.airbnb.user.model.User;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
//import com.mysql.cj.protocol.Message;
import javax.mail.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static com.example.airbnb.TestCreationFactory.randomLong;
import static com.example.airbnb.TestCreationFactory.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccommodationServiceUnitTests {
    @InjectMocks
    private AccommodationService accommodationService;

    @Mock
    private AccommodationRepository accommodationRepository;

    @Mock
    private AccommodationMapper accommodationMapper;

    @Mock
    private UserService userService;

    private GreenMail greenMail;

    @Mock
    private JavaMailSender emailSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accommodationService = new AccommodationService(accommodationMapper, accommodationRepository, userService);
    }

    @Test
    void findAll() {
        List<Accommodation> accommodations = TestCreationFactory.listOf(Accommodation.class);
        final Page<Accommodation> accommodationPages = new PageImpl<>(accommodations);
        Pageable paging = PageRequest.of(0, 100);
        when(accommodationRepository.findAll(paging)).thenReturn(accommodationPages);

        Page<Accommodation> accommodationPage1 = accommodationService.findAll();

        Assertions.assertEquals(accommodationPages.getTotalElements(), accommodationPage1.getTotalElements());
    }

    @Test
    void findById(){
        Accommodation accommodation = TestCreationFactory.accommodation();
        when(accommodationRepository.findById(accommodation.getId())).thenReturn(java.util.Optional.of(accommodation));
        Accommodation accommodation1 = accommodationService.findById(accommodation.getId());
        assertEquals(accommodation.getId(), accommodation1.getId());
    }

    @Test
    void delete(){
        Accommodation accommodation = TestCreationFactory.accommodation();

        doNothing().when(accommodationRepository).deleteById(accommodation.getId());
//        when(accommodationRepository.findById(accommodation.getId())).thenReturn(java.util.Optional.of(accommodation));

        accommodationService.delete(accommodation.getId());

//        assertNull(accommodationService.findById(accommodation.getId()));

    }

    @Test
    void create(){
        User user = TestCreationFactory.user();
        Accommodation accommodation = TestCreationFactory.accommodationWithUser(user);
        AccommodationDTO accommodationDTO = TestCreationFactory.accommodationDTOWithUserId(user.getId(), accommodation.getId());

        when(userService.findById(accommodationDTO.getUser_id())).thenReturn(user);
        when(accommodationRepository.save(accommodation)).thenReturn(accommodation);
        when(accommodationMapper.fromDto(accommodationDTO)).thenReturn(accommodation);
        when(accommodationMapper.toDto(accommodation)).thenReturn(accommodationDTO);

        AccommodationDTO accommodationDTO1 = accommodationService.create(accommodationDTO);
        assertEquals(accommodation.getId(), accommodationDTO1.getId());
    }

    @Test
    void update(){
        User user = TestCreationFactory.user();
        Accommodation accommodation = TestCreationFactory.accommodationWithUser(user);
        AccommodationDTO accommodationDTO = TestCreationFactory.accommodationDTOWithUserId(user.getId(), accommodation.getId());
        accommodationDTO.setName("new name");

        when(accommodationRepository.findById(accommodation.getId())).thenReturn(java.util.Optional.of(accommodation));
        when(accommodationMapper.toDto(accommodation)).thenReturn(accommodationDTO);
        when(accommodationRepository.save(accommodation)).thenReturn(accommodation);
        when(userService.findById(accommodationDTO.getUser_id())).thenReturn(user);

        AccommodationDTO accommodationDTO1 = accommodationService.edit(accommodation.getId(), accommodationDTO);
        assertEquals("new name", accommodationDTO1.getName());
    }

    @Test
    void save(){
        Accommodation accommodation = TestCreationFactory.accommodation();

        when(accommodationRepository.save(accommodation)).thenReturn(accommodation);

        accommodationService.save(accommodation);

    }

    @Test
    void sendEmail() throws MessagingException {
        greenMail = new GreenMail(ServerSetupTest.SMTP);
        greenMail.start();

        User user = User.builder().id(randomLong())
                .email("horvat.diana2000@gmail.com").password(randomString()).username(randomString()).build();
        Accommodation accommodation = TestCreationFactory.accommodationWithUser(user);

        when(accommodationRepository.findById(accommodation.getId())).thenReturn(java.util.Optional.of(accommodation));
        accommodationService.sendEmail(accommodation.getId());

        Message[] messages = greenMail.getReceivedMessages();
//        assertEquals(1, messages.length);
        assertEquals(accommodation.getName() + " Accommodation Details", messages[0].getSubject());
//        String body = GreenMailUtil.getBody(messages[0]).replaceAll("=\r?\n", "");
//        assertEquals("test message", body);
    }

    @Test
    void exportPDF() throws IOException {
        Accommodation accommodation = TestCreationFactory.accommodation();
        when(accommodationRepository.findById(accommodation.getId())).thenReturn(java.util.Optional.of(accommodation));

        ResponseEntity<byte[]> response = accommodationService.exportPDF(accommodation.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}