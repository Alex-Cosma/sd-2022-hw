package com.lab4.demo.stock;

import com.lab4.demo.price.model.StockPrice;
import com.lab4.demo.stock.model.Stock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StockServiceTest {
    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @Test
    public void testLoadPricesForStock() {
        Stock stock = new Stock().builder().name("Microsoft").symbol("MSFT").build();
        Stock stock1 = new Stock().builder().name("Apple").symbol("AAPL").build();
        Stock stock2 = new Stock().builder().name("Google").symbol("GOOG").build();
        stockRepository.saveAll(java.util.Arrays.asList(stock, stock1, stock2));
        stockService.loadPricesForAllStocks();
    }
}
