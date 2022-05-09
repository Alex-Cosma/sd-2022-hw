package com.example.bookstore.book;

import com.example.bookstore.book.dto.BookDTO;
import com.example.bookstore.book.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO toDto(Book item);
    Book fromDto(BookDTO item);

}
