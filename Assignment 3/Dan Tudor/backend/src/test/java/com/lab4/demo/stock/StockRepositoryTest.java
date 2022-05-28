package com.lab4.demo.stock;

import com.lab4.demo.stock.model.Stock;
import com.lab4.demo.stock.model.dto.StockDTO;
import org.apache.catalina.LifecycleState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class StockRepositoryTest {
    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    @AfterEach
    public void cleanUp() {
        stockRepository.deleteAll();
    }

    @Test
    public void findBySymbol() {
        Stock stock = new Stock().builder().name("Apple").symbol("AAPL").build();
        stockRepository.save(stock);

        Stock stock1 = stockRepository.findBySymbol("AAPL");
        assertTrue(stock1.getSymbol().equals("AAPL"));
    }

    @Test
    public void findAll() {
        Stock stock = new Stock().builder().name("Apple").symbol("AAPL").build();
        stockRepository.save(stock);

        Stock stock1 = new Stock().builder().name("Microsoft").symbol("MSFT").build();
        stockRepository.save(stock1);

        Stock stock2 = new Stock().builder().name("Google").symbol("GOOG").build();
        stockRepository.save(stock2);

        List<Stock> stocks = stockRepository.findAll();
        assertTrue(stocks.size() == 3);
    }

    @Test
    public void findBySymbolAndName() {
        Stock stock = new Stock().builder().name("Apple").symbol("AAPL").build();
        Stock stock1 = new Stock().builder().name("Microsoft").symbol("MSFT").build();
        Stock stock2 = new Stock().builder().name("Google").symbol("GOOG").build();
        stockRepository.saveAll(List.of(stock, stock1, stock2));

        List<Stock> stocks = stockRepository.findAllByNameLikeOrSymbolLike("AAPL", "AAPL");
        assertTrue(stocks.get(0).getName().equals("Apple"));

        List<Stock> stocks1 = stockRepository.findAllByNameLikeOrSymbolLike( "Microsoft", "Microsoft");
        assertTrue(stocks1.get(0).getSymbol().equals("MSFT"));

    }
}
