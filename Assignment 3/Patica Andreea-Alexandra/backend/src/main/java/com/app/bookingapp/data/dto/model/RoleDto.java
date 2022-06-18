package com.app.bookingapp.data.dto.model;

import com.app.bookingapp.data.sql.entity.enums.ERole;
import lombok.Data;

import java.io.Serializable;

@Data
public class RoleDto implements Serializable {
    private final ERole name;
    private final String description;

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof RoleDto)) {
            return false;
        }

        RoleDto c = (RoleDto) o;

        return this.name.equals(c.getName())
                && this.description.equals(c.getDescription());
    }
}
