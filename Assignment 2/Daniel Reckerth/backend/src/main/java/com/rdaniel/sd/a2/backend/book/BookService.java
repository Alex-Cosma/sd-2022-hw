package com.rdaniel.sd.a2.backend.book;

import com.rdaniel.sd.a2.backend.book.dto.BookDto;
import com.rdaniel.sd.a2.backend.book.dto.BookFilterRequestDto;
import com.rdaniel.sd.a2.backend.book.mapper.BookMapper;
import com.rdaniel.sd.a2.backend.book.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static com.rdaniel.sd.a2.backend.book.BookSpecification.specificationFromFilter;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final BookMapper bookMapper;

  public List<BookDto> findAll() {
    return bookMapper.toDtoList(bookRepository.findAll());
  }

  public BookDto findById(Long id) throws EntityNotFoundException {
    final Optional<Book> byId = bookRepository.findById(id);
    if (byId.isEmpty()) {
      throw new EntityNotFoundException("Book with id " + id + " not found");
    }
    return bookMapper.toDto(byId.get());
  }

  public BookDto save(BookDto bookDto) {
    return bookMapper.toDto(bookRepository.save(bookMapper.toEntity(bookDto)));
  }

  public BookDto update(Long id, BookDto bookDto) throws EntityNotFoundException {
    final Book book = bookRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found"));

    book.setTitle(bookDto.getTitle());
    book.setAuthor(bookDto.getAuthor());
    book.setGenre(bookDto.getGenre());
    book.setQuantity(bookDto.getQuantity());
    book.setPrice(bookDto.getPrice());

    return bookMapper.toDto(bookRepository.save(book));
  }

  public void delete(Long id) throws EntityNotFoundException {
    final Optional<Book> byId = bookRepository.findById(id);
    if (byId.isEmpty()) {
      throw new EntityNotFoundException("Book with id " + id + " not found");
    }
    bookRepository.deleteById(id);
  }

  public Page<BookDto> findAllFiltered(BookFilterRequestDto bookFilterRequestDto, Pageable pageable) {
    return bookRepository.findAll(specificationFromFilter(bookFilterRequestDto), pageable)
        .map(bookMapper::toDto);
  }

  public BookDto sellBooks(Long id, int quantity) throws EntityNotFoundException {
    final Book book = bookRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found"));

    if(book.getQuantity() - quantity < 0) {
      throw new RuntimeException("Not enough books to sell");
    }

    book.setQuantity(book.getQuantity() - quantity);

    return bookMapper.toDto(bookRepository.save(book));
  }
}
