package com.app.banking.data.mongo.entity;

import com.app.banking.data.dto.model.InterbankingTransferDetails;
import com.app.banking.service.TransferDetails;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferHistory {

    private String purpose;
    private InterbankingTransferDetails transferDetails;
    private TransferDetails internTransferDetails;

}
