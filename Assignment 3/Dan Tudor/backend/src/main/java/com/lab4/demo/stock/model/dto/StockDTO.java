package com.lab4.demo.stock.model.dto;

import com.lab4.demo.price.model.StockPrice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {
    private Long id;
    private String name;
    private String symbol;
    private List<StockPrice> stockPrices;
}
