package com.app.banking.data.sql.entity;

import com.app.banking.util.CsvWriteable;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "interbanking_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterbankingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "bankName", nullable = false, length = 64)
    private String bankName;

    @Column(name = "ibanPrefix", nullable = false, length = 64)
    private String ibanPrefix;

    @Column(name = "feesToTransfer", nullable = false)
    private Float feesToTransfer;

    @Column(name = "endpointToCall", nullable = false, length = 128)
    private String endpointToCall;

    @Column(name = "identifyToken", nullable = false, length = 64)
    private String identifyToken;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InterbankingDetail that = (InterbankingDetail) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}