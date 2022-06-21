package com.app.banking.data.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpensDto implements Serializable {
    private String name;
    private Float expectedAmount;
}
