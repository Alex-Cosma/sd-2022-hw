package com.lab4.demo.payment;

import com.lab4.demo.payment.model.DTO.PaymentDTO;
import com.lab4.demo.payment.model.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    Payment fromDto(PaymentDTO paymentDTO);

    PaymentDTO toDto(Payment payment);
}
