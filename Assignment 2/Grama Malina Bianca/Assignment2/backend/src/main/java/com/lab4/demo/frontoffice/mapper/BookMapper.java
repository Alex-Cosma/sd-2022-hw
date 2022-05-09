package com.lab4.demo.frontoffice.mapper;

import com.lab4.demo.frontoffice.model.Book;
import com.lab4.demo.frontoffice.model.dto.BookDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO toDto(Book book);
    Book fromDto(BookDTO bookDTO);
}
