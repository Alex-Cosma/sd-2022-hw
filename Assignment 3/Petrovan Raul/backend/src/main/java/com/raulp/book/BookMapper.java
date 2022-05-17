package com.raulp.book;

import com.raulp.book.model.Book;
import com.raulp.book.model.dto.BookDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO toDto(Book book);

    Book fromDto(BookDTO item);

}
