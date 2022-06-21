package com.app.banking.data.dto.model;

import com.app.banking.data.dto.model.DepositTypeDto;
import com.app.banking.data.dto.model.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositDto implements Serializable {
    private UserDto owner;
    private DepositTypeDto depositType;
    private LocalDateTime createdOn;
    private Float currentAmount;
    private LocalDateTime targetDate;
    private Float targetAmount;
}
