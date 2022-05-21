package com.lab4.demo.payment.model.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

    private Long id;
    private String name;
    private Long user_id;
    private Long track_id;
}
