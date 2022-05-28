package com.example.backend.dto.book;

import javax.validation.constraints.NotNull;

public class BookSoldDto {

    @NotNull
    public int id;

    @NotNull
    public int remainingStock;

    public BookSoldDto setId(int id) {
        this.id = id;
        return this;
    }

    public BookSoldDto setRemainingStock(int remainingStock) {
        this.remainingStock = remainingStock;
        return this;
    }
}
