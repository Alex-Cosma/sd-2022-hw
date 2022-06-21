package com.app.banking.data.dto.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTransferStatistics {

    private Long failedTransfers;
    private Long doneTransfers;
    private Long waitingTransfers;
    private Double totalSentMoney;
    private Double moneySuccessfullySent;

}
