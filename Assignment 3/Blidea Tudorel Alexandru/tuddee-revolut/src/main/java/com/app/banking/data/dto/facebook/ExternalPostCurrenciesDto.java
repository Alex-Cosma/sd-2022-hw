package com.app.banking.data.dto.facebook;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalPostCurrenciesDto {

    private List<ExternalFacebookComplexElement> elements;

}
