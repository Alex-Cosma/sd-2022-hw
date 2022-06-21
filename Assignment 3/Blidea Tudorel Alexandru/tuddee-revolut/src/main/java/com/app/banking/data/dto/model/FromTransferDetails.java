package com.app.banking.data.dto.model;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FromTransferDetails {

    private String firstName;
    private String lastName;
    private String email;
    private String iban;

}
