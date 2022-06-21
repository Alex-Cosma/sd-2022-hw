package com.app.banking.data.dto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetails {

    @NotBlank
    private String cardNumber;
    @NotBlank
    private String cvv;
    @NotNull
    private Float amount;
    @NotNull
    private String businessCIF;

}
