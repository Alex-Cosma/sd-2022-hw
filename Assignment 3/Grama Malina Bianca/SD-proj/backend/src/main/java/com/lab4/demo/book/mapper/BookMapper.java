package com.lab4.demo.book.mapper;

import com.lab4.demo.book.dto.BookDTO;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.EGenre;
import com.lab4.demo.book.model.Genre;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mappings({
            @Mapping(target = "genre", ignore = true)
    })
    BookDTO toDto(Book book);

    @Mappings({
            @Mapping(target = "genre", ignore = true)
    })
    Book fromDto(BookDTO bookDTO);

    @AfterMapping
    default void populateBookDTOGenre(Book book, @MappingTarget BookDTO bookDTO) {
        bookDTO.setGenre(book.getGenre().getName().toString());
    }

    @AfterMapping
    default void populateBookGenre(BookDTO bookDTO, @MappingTarget Book book) {
        EGenre eGenre = EGenre.valueOf(bookDTO.getGenre());
        book.setGenre(Genre.builder().name(eGenre).build());
    }
}
