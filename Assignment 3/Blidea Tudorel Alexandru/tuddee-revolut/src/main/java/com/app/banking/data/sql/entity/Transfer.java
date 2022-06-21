package com.app.banking.data.sql.entity;

import com.app.banking.data.sql.entity.enums.TransferStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "transferStatus", length = 16)
    @Enumerated(value = EnumType.STRING)
    private TransferStatus transferStatus;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "from", length = 36)
    private String from;
    @Column(name = "to", length = 36)
    private String to;
    @Column(name = "remainingAttempts")
    private Short remainingAttempts;
    @Column(name = "doneOn")
    private LocalDateTime doneOn;
    @Column(name = "startedOn")
    private LocalDateTime startedOn;

}
