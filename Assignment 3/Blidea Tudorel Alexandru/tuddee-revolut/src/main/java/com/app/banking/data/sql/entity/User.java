package com.app.banking.data.sql.entity;



import com.app.banking.data.sql.entity.enums.UserRole;
import com.app.banking.util.CsvWriteable;
import lombok.*;
import org.hibernate.Hibernate;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "noSqlId", nullable = false, length = 24)
    private String noSqlId;

    @Column(name = "firstName", nullable = false, length = 64)
    private String firstName;

    @Column(name = "lastName", nullable = false, length = 64)
    private String lastName;

    @Column(name = "username", nullable = false, length = 16)
    private String username;

    @Column(name = "passwordHash", nullable = false, length = 64)
    private String passwordHash;

    @Column(name = "email", nullable = false, length = 64)
    private String email;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false, length = 16)
    private UserRole role;

    @Column(name = "birthDate", nullable = false)
    private LocalDate birthDate;

    @Column(name = "profileImageName", length = 70)
    private String profileImageName;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User that = (User) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}