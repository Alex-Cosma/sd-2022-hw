package com.app.bookingapp.data.dto.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class BookDto implements Serializable {
    private final UserDto user;
    private final PropertyDto property;
    private final Date date;
    private final String noSqlId;

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof BookDto)) {
            return false;
        }

        BookDto c = (BookDto) o;

        return this.user.equals(c.getUser())
                && this.property.equals(c.getProperty())
                && this.date.equals(c.getDate())
                && this.noSqlId.equals(c.noSqlId);
    }
}
