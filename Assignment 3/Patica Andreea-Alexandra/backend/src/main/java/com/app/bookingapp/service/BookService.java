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
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.util.List;
import java.util.Optional;

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
        return bookRepository.findAll();
    }

    public List<Book> allBooksByUser(String username){
        return bookRepository.findAllByUserUsername(username);
    }

    public BookDto create(SimpleBookDto simpleBookDto){
        Optional<User> userOp = userRepository.findByUsername(simpleBookDto.getUsername());
        Optional<Property> propertyOp = propertyRepository.findPropertyByName(simpleBookDto.getProperty().getName());

        Book book = new Book();
        book.setDate(simpleBookDto.getDate());

        userOp.ifPresent(book::setUser);
        propertyOp.ifPresent(book::setProperty);

        BookDto bookDtoAdded;

        try{
            bookDtoAdded = bookMapper.bookToBookDto(bookRepository.save(book));
            Twilio.init("AC7f0ce2f681fbed6b1355149e3e7417eb", "ef6adc165654d0d259d0cce98da0c3fc");
            Message message = Message.creator(
                            //new com.twilio.type.PhoneNumber("+40786079644"),
                            new com.twilio.type.PhoneNumber("+40756285628"),
                            new com.twilio.type.PhoneNumber("+19895463499"),
                            "Your booking to location" + bookDtoAdded.getProperty().getName()
                                    + ", address " + bookDtoAdded.getProperty().getAddress()
                                    + ", on date " + bookDtoAdded.getDate() + " has been successfully registered!")
                    .create();

            System.out.println(message.getSid());

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
