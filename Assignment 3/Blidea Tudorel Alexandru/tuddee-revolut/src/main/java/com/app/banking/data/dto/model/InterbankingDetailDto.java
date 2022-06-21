package com.app.banking.data.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterbankingDetailDto implements Serializable {
    private String bankName;
    private String ibanPrefix;
    private Float feesToTransfer;
    private String endpointToCall;
}
