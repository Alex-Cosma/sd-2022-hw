package com.example.bookstore.book;

import com.example.bookstore.TestCreationFactory;
import com.example.bookstore.book.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testMock() {
        Book bookSaved = repository.save(TestCreationFactory.newBook());

        assertNotNull(bookSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(Book.builder().build());
        });
    }


    @Test
    public void testFindAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        repository.saveAll(books);
        List<Book> all = repository.findAll();

        assertEquals(books.size(), all.size());
    }

    @Test
    public void testFindAllOutOfStock() {
        List<Book> books=new ArrayList<>();
        for(int i=0;i<10;i++){
            books.add(TestCreationFactory.newBook());
            if(i<5){
                books.get(i).setQuantity(0);
            }
        }
        repository.saveAll(books);
        List<Book> all = repository.findAllByQuantityEquals(0);

        assertEquals(5,all.size());
    }

    @Test
    public void testFindAllByFilter() {
        List<Book> books=new ArrayList<>();
        for(int i=0;i<10;i++){
            books.add(TestCreationFactory.newBook());
        }
        books.get(0).setTitle("random");
        books.get(1).setTitle("Longer random");
        books.get(2).setAuthor("random");
        books.get(3).setAuthor("Longer random");
        books.get(3).setGenre("random");
        repository.saveAll(books);
        List<Book> all = repository.findAllByTitleLikeOrAuthorLikeOrGenreLike("%random%","%random%","%random%");
        System.out.println("size"+all.size());

        assertEquals(4,all.size());
    }

    @Test
    public void save(){
        assertEquals(0,repository.findAll().size());

        Book book=TestCreationFactory.newBook();
        repository.save(book);

        assertEquals(1,repository.findAll().size());
    }

    @Test
    public void update(){
        Book book = TestCreationFactory.newBook();
        repository.save(book);
        book.setTitle("New title");
        Book updatedBook = repository.save(book);

        assertEquals("New title",updatedBook.getTitle());
    }

    @Test
    public void delete(){
        Book book=TestCreationFactory.newBook();
        Book savedBook=repository.save(book);
        assertEquals(1,repository.findAll().size());
        repository.deleteById(savedBook.getId());

        assertEquals(0,repository.findAll().size());
    }
}