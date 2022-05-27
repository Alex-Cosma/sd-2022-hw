package com.lab4.demo.price.model;

import com.lab4.demo.stock.model.Stock;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class StockPrice {
    @Id
    private Long id;

    @Column
    private LocalDateTime dateTime;

    @Column
    private Double openPrice;

    @Column
    private Double highPrice;

    @Column
    private Double lowPrice;

    @Column
    private Double closePrice;

    @Column
    private Long volume;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;


}
