package com.app.banking.data.dto.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterbankingTransferDetails {

    private String bankName;
    private String iban;
    private Double amount;
    private FromTransferDetails fromDetails;

}
