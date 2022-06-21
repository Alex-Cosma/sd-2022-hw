package com.app.bookingapp.service;

import com.app.bookingapp.data.dto.mapper.BookMapper;
import com.app.bookingapp.data.dto.model.BookDto;
import com.app.bookingapp.data.dto.model.SimpleBookDto;
import com.app.bookingapp.data.sql.entity.Book;
import com.app.bookingapp.data.sql.entity.Property;
import com.app.bookingapp.data.sql.entity.User;
import com.app.bookingapp.data.sql.repo.BookRepository;
import com.app.bookingapp.data.sql.repo.PropertyRepository;
import com.app.bookingapp.data.sql.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    public BookDto findById(Long id){       //TODO test
        return bookRepository.findById(id)
                .map(bookMapper::bookToBookDto)
                .orElseThrow(() -> new RuntimeException(format("Book with id %s not found", id)));
    }

    public List<Book> findAll(){
//        return bookRepository.findAll().stream()
//                .map(bookMapper::bookToBookDto)
//                .collect(Collectors.toList());
        return bookRepository.findAll();
    }

    public List<Book> allBooksByUser(String username){
//        return bookRepository.findAllByUserUsername(username).stream()
//                .map(bookMapper::bookToBookDto)
//                .collect(Collectors.toList());
        return bookRepository.findAllByUserUsername(username);
    }

    public BookDto create(SimpleBookDto simpleBookDto){
        Optional<User> userOp = userRepository.findByUsername(simpleBookDto.getUsername());
        Optional<Property> propertyOp = propertyRepository.findPropertyByName(simpleBookDto.getProperty().getName());

//        BookDto bookDto = BookDto.builder()
//                .date(simpleBookDto.getDate())
//                .property(simpleBookDto.getProperty())


//        Book book = bookMapper.bookDtoToBook(bookDto);
        Book book = new Book();
        book.setDate(simpleBookDto.getDate());

        userOp.ifPresent(book::setUser);
        propertyOp.ifPresent(book::setProperty);

        BookDto bookDtoAdded;

        try{
            bookDtoAdded = bookMapper.bookToBookDto(bookRepository.save(book));
        }catch(Exception e){
            bookDtoAdded = null;
        }

        return bookDtoAdded;
    }

    public BookDto update(Long id, BookDto bookDto){
        Book bookEntity = bookMapper.bookDtoToBook(bookDto);
        bookEntity.setId(id);

        Optional<User> userOp = userRepository.findByUsername(bookDto.getUser().getUsername());
        Optional<Property> propertyOp = propertyRepository.findPropertyByName(bookDto.getProperty().getName());

        Book book = bookMapper.bookDtoToBook(bookDto);

        userOp.ifPresent(book::setUser);
        propertyOp.ifPresent(book::setProperty);

        BookDto bookDtoAdded;

        try{
            bookDtoAdded = bookMapper.bookToBookDto(bookRepository.save(book));
        }catch(Exception e){
            bookDtoAdded = null;
        }

        return bookDtoAdded;
    }

    public void delete(Long id){
        bookRepository.deleteById(id);
    }
}
