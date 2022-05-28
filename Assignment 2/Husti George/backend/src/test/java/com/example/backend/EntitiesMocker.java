package com.example.backend;

import com.example.backend.dto.book.BookCreationDto;

public class EntitiesMocker {

    public static BookCreationDto mockBookCreationDto() {
        return new BookCreationDto()
                .setAuthor(EntitiesMockerService.randomString())
                .setStock(EntitiesMockerService.randomDouble())
                .setGenre(EntitiesMockerService.randomString())
                .setTitle(EntitiesMockerService.randomString())
                .setPublishedDate(java.sql.Date.valueOf(String.valueOf(EntitiesMockerService.randomDate())))
                .setPrice(EntitiesMockerService.randomDouble());
    }
}
