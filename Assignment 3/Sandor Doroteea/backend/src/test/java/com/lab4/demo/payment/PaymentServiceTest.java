package com.lab4.demo.payment;

import com.lab4.demo.payment.model.DTO.PaymentDTO;
import com.lab4.demo.payment.model.Payment;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

class PaymentServiceTest {

    @InjectMocks
    private  PaymentService paymentService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private PaymentMapper paymentMapper;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentService = new PaymentService(reportServiceFactory,paymentMapper,paymentRepository);
    }

    @Test
    void createSession() {
        PaymentDTO paymentDTO = PaymentDTO.builder()
                .name("Name")
                .track_id(1L)
                .user_id(1L)
                .build();

        paymentService.createSession(paymentDTO);
    }

    @Test
    void report() throws IOException {
//        userRepository.save(User.builder().purchasedTracks(new HashSet<>()).build());
        //when(userRepository.findById(1L)).thenReturn(Optional.of(User.builder().username("ana").purchasedTracks(new HashSet<>()).build()));
//        when(reportServiceFactory.getReportService(ReportType.CSV).export(1L)).thenReturn("CSV");
//
//        String report = paymentService.export(ReportType.CSV, 1L);
//        Assertions.assertEquals("CSV",report);
//
//        when(reportServiceFactory.getReportService(ReportType.PDF).export(1L)).thenReturn("PDF");
//
//        report = paymentService.export(ReportType.PDF, 1L);
//        Assertions.assertEquals("PDF",report);
    }

    @Test
    void findAll(){
        PaymentDTO paymentDTO = PaymentDTO.builder()
                .id(1L)
                .name("Payment")
                .track_id(1L)
                .user_id(1L)
                .build();
        List<PaymentDTO> paymentDTOList = new ArrayList<>();
        paymentDTOList.add(paymentDTO);

        Payment payment = Payment.builder()
                .id(1L)
                .name("Payment")
                .track_id(1L)
                .user_id(1L)
                .build();
        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);

        when(paymentRepository.findAll()).thenReturn(paymentList);
        when(paymentMapper.toDto(payment)).thenReturn(paymentDTO);

        List<PaymentDTO> paymentDTOS = paymentService.findAll();

        Assertions.assertEquals(paymentDTOList.size(),paymentDTOS.size());
    }

    @Test
    void save(){
        PaymentDTO paymentDTO = PaymentDTO.builder()
                .id(1L)
                .name("Payment")
                .track_id(1L)
                .user_id(1L)
                .build();

        Payment payment = Payment.builder()
                .id(1L)
                .name("Payment")
                .track_id(1L)
                .user_id(1L)
                .build();

        when(paymentMapper.toDto(paymentRepository.save(paymentMapper.fromDto(paymentDTO)))).thenReturn(paymentDTO);

        PaymentDTO paymentDTO1 = paymentService.savePayment(paymentDTO);

        Assertions.assertEquals(paymentDTO.getName(),paymentDTO1.getName());
    }

}