package com.app.banking.data.dto.email;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MailAuth {

    private String user;
    private String pass;

}
