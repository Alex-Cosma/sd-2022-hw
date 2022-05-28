package com.lab4.demo.payment;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.payment.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @BeforeEach
    public void beforeEach() {
        paymentRepository.deleteAll();
    }

    @Test
    void testMock() {
        Payment payment = paymentRepository.save(Payment.builder()
                .name("Payment")
                .track_id(1L)
                .user_id(1L)
                .build());
        assertNotNull(payment);

        assertThrows(DataIntegrityViolationException.class, () -> {
            paymentRepository.save(Payment.builder().build());
        });
    }

    @Test
    void testFindAll() {
        List<Payment> Payments = TestCreationFactory.listOf(Payment.class);
        paymentRepository.saveAll(Payments);
        List<Payment> all = paymentRepository.findAll();
        assertEquals(Payments.size(), all.size());
    }

    @Test
    void create(){
        Payment payment = paymentRepository.save(Payment.builder()
                .name("Payment")
                .track_id(1L)
                .user_id(1L)
                .build());

        assertEquals(payment.getName(), paymentRepository.findById(payment.getId()).get().getName());
    }

}