package com.app.bookingapp.data.sql.entity;

import com.app.bookingapp.data.sql.entity.enums.EAccountType;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "account_type")
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, length = 64)
    private EAccountType name;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

}