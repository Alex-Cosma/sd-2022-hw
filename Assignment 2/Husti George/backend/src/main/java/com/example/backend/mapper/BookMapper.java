package com.example.backend.mapper;

import com.example.backend.dto.book.BookCreationDto;
import com.example.backend.dto.book.BookGetDto;
import com.example.backend.dto.book.BookSoldDto;
import com.example.backend.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book bookCreationDtoToBook(BookCreationDto bookCreationDto);

    BookGetDto bookToBookGetDto(Book book);

    @Mapping(source = "stock", target = "remainingStock")
    BookSoldDto bookSoldDtoToBook(Book book);
}