package com.lab4.demo.stock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lab4.demo.price.model.StockPrice;
import com.lab4.demo.user.model.User;
import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String symbol;

    @Column(length = 100, nullable = false)
    private String name;

    @OneToMany(mappedBy = "stock",fetch = FetchType.EAGER)
    //@OneToMany
    @JsonIgnore
    @OrderBy("dateTime ASC")
    private List<StockPrice> stockPrices;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "stock_user",
            joinColumns = @JoinColumn(name = "stock_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Builder.Default
    private Set<User> users = new HashSet<>();

}
