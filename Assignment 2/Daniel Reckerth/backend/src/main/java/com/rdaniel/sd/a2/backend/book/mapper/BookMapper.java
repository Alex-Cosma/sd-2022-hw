package com.rdaniel.sd.a2.backend.book.mapper;

import com.rdaniel.sd.a2.backend.book.dto.BookDto;
import com.rdaniel.sd.a2.backend.book.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

  BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

  BookDto toDto(Book book);

  Book toEntity(BookDto bookDto);

  List<BookDto> toDtoList(List<Book> books);
}
