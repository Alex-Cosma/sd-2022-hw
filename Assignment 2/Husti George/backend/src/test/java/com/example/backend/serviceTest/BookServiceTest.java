package com.example.backend.serviceTest;

import com.example.backend.EntitiesMocker;
import com.example.backend.dto.book.BookCreationDto;
import com.example.backend.dto.book.BookGetDto;
import com.example.backend.dto.book.BookSoldDto;
import com.example.backend.repository.BookRepository;
import com.example.backend.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookServiceTest {

    private final BookRepository bookRepository;
    private final BookService bookService;

    public BookServiceTest(BookRepository bookRepository, BookService bookService) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    @Test
    public void sellBook() {

        BookCreationDto createdBook = EntitiesMocker.mockBookCreationDto();

        BookGetDto bookGetDto = bookService.createBook(createdBook);
        BookSoldDto bookSoldDto = bookService.sellBook(bookGetDto.id, 5);

        assertEquals(bookSoldDto.remainingStock, createdBook.stock - 5);
    }
}
