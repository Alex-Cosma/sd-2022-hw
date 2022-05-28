package com.lab4.demo.stock;

import com.lab4.demo.stock.model.dto.StockDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;
import static com.lab4.demo.UrlMapping.STOCKS_ID;

@RestController
@RequestMapping(STOCKS)
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @GetMapping
    public List<StockDTO> findAll() {
        return stockService.findAll();
    }

    @PostMapping
    public StockDTO create(@RequestBody StockDTO stockDTO) {
        return stockService.create(stockDTO);
    }

    @DeleteMapping(STOCKS_ID)
    public void delete(@PathVariable Long id) {
        stockService.delete(id);
    }

    @PutMapping(STOCKS_ID)
    public StockDTO update(@PathVariable Long id, @RequestBody StockDTO stockDTO) {
        return stockService.update(id, stockDTO);
    }


}
