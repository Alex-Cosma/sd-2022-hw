package com.app.bookingapp.data.sql.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 64)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 64)
    private String lastName;

    @Column(name = "username", nullable = false, length = 64)
    private String username;

    @Column(name = "email", nullable = false, length = 64)
    private String email;

    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @Column(name = "phone_number", nullable = false, length = 13)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "account_type_id", nullable = false)
    private AccountType accountType;

    @Column(name = "no_sql_id")
    private Long noSqlId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}