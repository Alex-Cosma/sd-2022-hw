package com.app.banking.data.dto.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactsDto {

    private Integer id;
    private String lastName;
    private String firstName;
    private String email;
    private List<String> ibans;
    private String profileImageName;

}
