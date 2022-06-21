package com.app.banking.data.dto.email;

import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalWelcomeEmailDto {

    private String to;
    private List<String> currentOffers;

}
