package com.app.banking.data.dto.email;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalCurrencyEmailDto {

    private List<String> recipients;
    private Map<String, Double> currencies;

}
