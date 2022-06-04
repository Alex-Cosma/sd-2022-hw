package com.rdaniel.sd.a2.backend.book.mapper;

import com.rdaniel.sd.a2.backend.book.dto.BookDto;
import com.rdaniel.sd.a2.backend.book.model.Book;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.rdaniel.sd.a2.backend.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

  @Test
  void toDto() {
    final Book book = newBook();
    final BookDto bookDto = BookMapper.INSTANCE.toDto(book);
    assertEquals(book.getTitle(), bookDto.getTitle());
    assertEquals(book.getAuthor(), bookDto.getAuthor());
    assertEquals(book.getPrice(), bookDto.getPrice());
    assertEquals(book.getQuantity(), bookDto.getQuantity());
  }

  @Test
  void toEntity() {
    final BookDto bookDto = newBookDto();
    final Book book = BookMapper.INSTANCE.toEntity(bookDto);
    assertEquals(bookDto.getTitle(), book.getTitle());
    assertEquals(bookDto.getAuthor(), book.getAuthor());
    assertEquals(bookDto.getPrice(), book.getPrice());
    assertEquals(bookDto.getQuantity(), book.getQuantity());
  }

  @Test
  void toDtoList() {
    final List<Book> books = listOf(Book.class, 15);
    final List<BookDto> bookDtos = BookMapper.INSTANCE.toDtoList(books);
    assertEquals(books.size(), bookDtos.size());
  }
}