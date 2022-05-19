package com.example.demo.book;

import com.example.demo.book.model.Book;
import com.example.demo.book.model.dto.BookDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO toDto(Book item);

    Book fromDto(BookDTO item);

}