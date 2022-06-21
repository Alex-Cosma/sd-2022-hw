package com.app.banking.service;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferDetails {

    private String cardNumber;
    private String ibanToTransfer;
    private Double amount;

}
