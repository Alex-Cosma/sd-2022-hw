package com.lab4.demo.stock;

import com.lab4.demo.stock.model.Stock;
import com.lab4.demo.stock.model.dto.StockDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockMapper {
    StockDTO toDto(Stock stock);
    Stock fromDto(StockDTO stockDTO);
}
