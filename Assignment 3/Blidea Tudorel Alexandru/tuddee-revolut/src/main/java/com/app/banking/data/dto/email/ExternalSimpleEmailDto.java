package com.app.banking.data.dto.email;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalSimpleEmailDto {

    private List<String> recipients;
    private String subject;
    private String text;

}
