package com.app.banking.data.dto.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReceivedMoneyStatistics {

    private Double totalReceivedMoney;
    private Integer totalTransfersReceived;
    private Long totalDoneTransfersReceived;
    private Double totalSuccessfulReceivedMoney;

}
