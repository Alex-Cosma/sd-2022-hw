package com.lab4.demo.investment.model;

import com.lab4.demo.stock.model.Stock;
import com.lab4.demo.user.model.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private int quantity;

    @Column(length = 100, nullable = false)
    private String symbol;

    @Column
    private double price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
