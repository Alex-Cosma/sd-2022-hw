package com.lab4.demo.stock;

import com.lab4.demo.stock.model.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findBySymbol(String symbol);
    Optional<Stock> findByName(String name);
    Optional<Stock> findBySymbolOrName(String symbol, String name);
    Optional<Stock> findById(Long id);
    Page<Stock> findAll(Pageable pageable);
    List<Stock> findAll();
    List<Stock> findAllByNameLikeOrSymbolLike(String name, String symbol);
}
