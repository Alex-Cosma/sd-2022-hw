package com.app.bookingapp.data.dto.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class SimpleBookDto implements Serializable {
    private final String username;
    private final PropertyDto property;
    private final Date date;
}
