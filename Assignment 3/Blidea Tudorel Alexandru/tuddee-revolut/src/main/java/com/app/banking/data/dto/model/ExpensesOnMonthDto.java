package com.app.banking.data.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpensesOnMonthDto implements Serializable {
    private BusinessDto owner;
    private ExpensDto expense;
    private LocalDateTime onDate;
    private Float currentValue;
}
