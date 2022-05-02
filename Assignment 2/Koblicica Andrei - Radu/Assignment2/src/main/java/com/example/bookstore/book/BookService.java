package com.example.bookstore.book;

import com.example.bookstore.book.dto.BookDTO;
import com.example.bookstore.book.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;



    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDTO> findAllOutOfStock(){
        return bookRepository.findAllByQuantityEquals(0).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
    public List<BookDTO> findAllByFilter(String filter){
        return bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike("%"+filter+"%","%"+filter+"%","%"+filter+"%").stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO item) {
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(item)
        ));
    }

    public void delete(Long id){
        bookRepository.deleteById(id);
    }

    public BookDTO update(Long id, BookDTO bookDTO){
        Book book = findById(id);
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setGenre(bookDTO.getGenre());
        book.setPrice(bookDTO.getPrice());
        book.setQuantity(bookDTO.getQuantity());
        return bookMapper.toDto(
                bookRepository.save(book)
        );
    }


    public BookDTO sell(Long id, BookDTO bookDTO) {
        Book book = findById(id);
        book.setQuantity(bookDTO.getQuantity()-1);
        return bookMapper.toDto(
                bookRepository.save(book)
        );

    }



}
