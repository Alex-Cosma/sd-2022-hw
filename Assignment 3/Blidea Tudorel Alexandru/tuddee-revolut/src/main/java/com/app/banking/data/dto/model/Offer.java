package com.app.banking.data.dto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Offer {

    private Integer id;
    private String name;
    private String description;
    private String available;

}
