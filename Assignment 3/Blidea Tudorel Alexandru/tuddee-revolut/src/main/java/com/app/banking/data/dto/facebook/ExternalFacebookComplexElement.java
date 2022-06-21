package com.app.banking.data.dto.facebook;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalFacebookComplexElement {

    private String message;
    private List<String> list;
    private boolean ordered;

}
