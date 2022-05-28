package com.lab4.demo.payment;

import com.lab4.demo.payment.model.DTO.PaymentDTO;
import com.lab4.demo.payment.model.Payment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PaymentServiceIntegrationTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository.deleteAll();
    }

    @Test
    void findAll(){
        PaymentDTO payment =PaymentDTO.builder()
                .name("Payment")
                .track_id(1L)
                .user_id(1L)
                .build();

        paymentService.savePayment(payment);
        Assertions.assertEquals(1,paymentRepository.findAll().size());
    }

    @Test
    void save(){
        PaymentDTO payment =PaymentDTO.builder()
                .name("Payment")
                .track_id(1L)
                .user_id(1L)
                .build();

        PaymentDTO paymentDTO =  paymentService.savePayment(payment);
        Assertions.assertEquals(payment.getName(),paymentDTO.getName());

    }
}