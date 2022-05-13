package com.assignment2.bookstoreapp.book.validator;

import com.assignment2.bookstoreapp.book.model.Book;
import com.assignment2.bookstoreapp.book.model.dto.BookDTO;


public class BookValidator {

    public static Book updateValidation(Book book, BookDTO bookDTO){
        if(bookDTO.getTitle() != null)
            if(!bookDTO.getTitle().isEmpty())
                book.setTitle(bookDTO.getTitle());

        if(bookDTO.getAuthor() != null)
            if(!bookDTO.getAuthor().isEmpty())
                book.setAuthor(bookDTO.getAuthor());

        if(bookDTO.getGenre() != null)
            if(!bookDTO.getGenre().isEmpty())
                book.setGenre(bookDTO.getGenre());

        book.setPrice(bookDTO.getPrice());
        book.setQuantity(bookDTO.getQuantity());
        return book;
    }
}
