package com.app.bookingapp.data.dto.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String email;
    private final String password;
    private final String phoneNumber;
    private final AccountTypeDto accountType;
    private final Long noSqlId;
    private final RoleDto role;

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof UserDto)) {
            return false;
        }

        UserDto c = (UserDto) o;

        return this.firstName.equals(c.getFirstName())
                && this.lastName.equals(c.getLastName())
                && this.username.equals(c.getUsername())
                && this.email.equals(c.getEmail())
                && this.password.equals(c.getPassword())
                && this.phoneNumber.equals(c.getPhoneNumber())
                && this.noSqlId.equals(c.getNoSqlId())
                && this.accountType.equals(c.getAccountType())
                && this.role.equals(c.getRole());
    }
}
