package com.app.banking.data.dto.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MailTransporter {

    private String host;
    private Boolean secure;
    private String service;
    private MailAuth auth;
    private Integer port;

}
