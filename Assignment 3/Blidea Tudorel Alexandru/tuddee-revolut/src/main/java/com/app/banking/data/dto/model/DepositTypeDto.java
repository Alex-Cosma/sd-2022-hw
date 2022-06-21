package com.app.banking.data.dto.model;

import com.app.banking.data.sql.entity.enums.EDepositType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositTypeDto implements Serializable {
    private String name;
    private String description;
}
