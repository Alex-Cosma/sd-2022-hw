package com.app.banking.data.dto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private String firstName;
    private String lastName;
    private String username;
    private String passwordHash;
    private String email;
    private String role;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    private LocalDateTime birthDate;
    private String profileImageName;

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof UserDto)) {
            return false;
        }

        UserDto u = (UserDto) o;

        return this.firstName.equals(u.getFirstName())
                && this.lastName.equals(u.getLastName())
                && this.username.equals(u.getUsername())
                && this.passwordHash.equals(u.getPasswordHash())
                && this.email.equals(u.getEmail())
                && this.role.equals(u.getRole())
                && this.birthDate.equals(u.getBirthDate());
    }

}
