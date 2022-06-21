package com.rdaniel.sd.a2.backend.book;

import com.rdaniel.sd.a2.backend.TestCreationFactory;
import com.rdaniel.sd.a2.backend.book.dto.BookFilterRequestDto;
import com.rdaniel.sd.a2.backend.book.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static com.rdaniel.sd.a2.backend.book.BookSpecification.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.ASC;

@SpringBootTest
class BookRepositoryTest {

  @Autowired
  private BookRepository bookRepository;

  @BeforeEach
  void beforeEach() {
    bookRepository.deleteAll();
  }

  @Test
  void testMock() {
    Book book = TestCreationFactory.newBook();
    Book savedBook = bookRepository.save(book);

    assertNotNull(savedBook);
    assertThrows(DataIntegrityViolationException.class, () -> bookRepository.save(Book.builder().build()));
  }

  @Test
  void testFindAll() {
    List<Book> books = TestCreationFactory.listOf(Book.class, 15);
    bookRepository.saveAll(books);
    List<Book> retrievedBooks = bookRepository.findAll();
    assertEquals(books.size(), retrievedBooks.size());
  }

  @Test
  void testSimpleSpecificationQuery_SimilarNames() {
    Book firstBook = Book.builder()
        .author("John Steinbeck")
        .title("The Grapes of Wrath")
        .genre("Novel")
        .quantity(20)
        .price(25.99)
        .build();

    Book secondBook = Book.builder()
        .author("John R.R. Tolkien")
        .title("Lord Of The Rings: The Fellowship of the Ring")
        .genre("Fantasy Fiction")
        .quantity(12)
        .price(30.00)
        .build();

    Book thirdBook = Book.builder()
        .author("John Irving")
        .title("The Cider House Rules")
        .genre("Bildungsromane")
        .quantity(2)
        .price(35.20)
        .build();

    Book fourthBook = Book.builder()
        .author("John Kennedy")
        .title("A Confederacy of Dunces")
        .genre("Tragedy")
        .quantity(2)
        .price(30.00)
        .build();

    List<Book> toBeFound = List.of(firstBook, secondBook, thirdBook, fourthBook);
    List<Book> books = TestCreationFactory.listOf(Book.class, 20);
    books.addAll(toBeFound);
    bookRepository.saveAll(books);
    final int sortedPage = 0;
    final int sortedPageSize = 10;
    final PageRequest first10AscByAuthor = PageRequest.of(sortedPage, sortedPageSize, Sort.by(ASC, "author"));
    final Page<Book> pagedResultSortAsc = bookRepository.findAll(similarAuthor("%John%"), first10AscByAuthor);
    assertTrue(pagedResultSortAsc.hasContent());
    assertEquals(4, pagedResultSortAsc.getNumberOfElements());
    assertEquals(sortedPage, pagedResultSortAsc.getNumber());

    final List<Book> pagedResultSortedContent = new ArrayList<>(pagedResultSortAsc.getContent());
    assertEquals(4, pagedResultSortedContent.size());

    final Book firstBookAsc = pagedResultSortedContent.get(0);
    pagedResultSortedContent.remove(0);

    assertTrue(pagedResultSortedContent.stream().anyMatch(book -> firstBookAsc.getAuthor().compareTo(book.getAuthor()) < 0));
  }

  @Test
  void testSimpleSpecificationQuery_SimilarPrices() {
    Book firstBook = Book.builder()
        .author("John Steinbeck")
        .title("The Grapes of Wrath")
        .genre("Novel")
        .quantity(20)
        .price(25.99)
        .build();

    Book secondBook = Book.builder()
        .author("John R.R. Tolkien")
        .title("Lord Of The Rings: The Fellowship of the Ring")
        .genre("Fantasy Fiction")
        .quantity(12)
        .price(30.00)
        .build();

    Book thirdBook = Book.builder()
        .author("John Irving")
        .title("The Cider House Rules")
        .genre("Bildungsromane")
        .quantity(2)
        .price(35.20)
        .build();

    Book fourthBook = Book.builder()
        .author("John Kennedy")
        .title("A Confederacy of Dunces")
        .genre("Tragedy")
        .quantity(2)
        .price(30.00)
        .build();

    List<Book> toBeFound = List.of(firstBook, secondBook, thirdBook, fourthBook);
    List<Book> books = TestCreationFactory.listOf(Book.class, 20);
    books.addAll(toBeFound);
    bookRepository.saveAll(books);
    final int sortedPage = 0;
    final int sortedPageSize = 10;
    final PageRequest first10AscByAuthor = PageRequest.of(sortedPage, sortedPageSize, Sort.by(ASC, "price"));
    final Page<Book> pagedResultSortAsc = bookRepository.findAll(similarPrice(30.00), first10AscByAuthor);
    assertTrue(pagedResultSortAsc.hasContent());
    assertEquals(sortedPageSize, pagedResultSortAsc.getNumberOfElements());
    assertEquals(sortedPage, pagedResultSortAsc.getNumber());

    final List<Book> pagedResultSortedContent = new ArrayList<>(pagedResultSortAsc.getContent());
    assertEquals(sortedPageSize, pagedResultSortedContent.size());

    final Book firstBookAsc = pagedResultSortedContent.get(0);
    pagedResultSortedContent.remove(0);

    assertTrue(pagedResultSortedContent.stream().anyMatch(book -> firstBookAsc.getPrice() <= book.getPrice()));
  }

  @Test
  void testCombinedSpecificationQuery_SimilarNamesAndSimilarTitle() {
    Book firstBook = Book.builder()
        .author("John R.R. Tolkien")
        .title("Lord Of The Rings: The Fellowship of the Ring")
        .genre("Fantasy Fiction")
        .quantity(10)
        .price(35.2)
        .build();

    Book secondBook = Book.builder()
        .author("John R.R. Tolkien")
        .title("Lord Of The Rings: The Return of the Kings")
        .genre("Fantasy Fiction")
        .quantity(12)
        .price(30.00)
        .build();

    Book thirdBook = Book.builder()
        .author("John Suzuki")
        .title("The Ring")
        .genre("Fantasy Fiction")
        .quantity(3)
        .price(45.00)
        .build();

    List<Book> toBeFound = List.of(firstBook, secondBook, thirdBook);
    List<Book> books = TestCreationFactory.listOf(Book.class, 20);
    books.addAll(toBeFound);
    bookRepository.saveAll(books);
    final int sortedPage = 0;
    final int sortedPageSize = 10;
    final PageRequest first10AscByAuthor = PageRequest.of(sortedPage, sortedPageSize, Sort.by(ASC, "author").and(Sort.by(ASC, "title")));
    final Page<Book> pagedResultSortAsc = bookRepository.findAll(similarAuthor("%John%").and(similarTitle("%Ring%")), first10AscByAuthor);
    assertTrue(pagedResultSortAsc.hasContent());
    assertEquals(3, pagedResultSortAsc.getNumberOfElements());
    assertEquals(sortedPage, pagedResultSortAsc.getNumber());

    final List<Book> pagedResultSortedContent = new ArrayList<>(pagedResultSortAsc.getContent());
    assertEquals(3, pagedResultSortedContent.size());

    final Book firstBookAsc = pagedResultSortedContent.get(0);
    pagedResultSortedContent.remove(0);

    assertTrue(pagedResultSortedContent.stream().anyMatch(book -> firstBookAsc.getAuthor().compareTo(book.getAuthor()) < 0
        && firstBookAsc.getTitle().compareTo(book.getTitle()) < 0));
  }

  @Test
  void testComplexSpecificationQuery() {
    Book firstBook = Book.builder()
        .author("John R.R. Tolkien")
        .title("Lord Of The Rings: The Fellowship of the Ring")
        .genre("Fantasy Fiction")
        .quantity(10)
        .price(35.2)
        .build();

    Book secondBook = Book.builder()
        .author("John R.R. Tolkien")
        .title("Lord Of The Rings: The Return of the Kings")
        .genre("Fantasy Fiction")
        .quantity(12)
        .price(30.00)
        .build();

    Book thirdBook = Book.builder()
        .author("John Suzuki")
        .title("The Ring")
        .genre("Fantasy Fiction")
        .quantity(3)
        .price(45.00)
        .build();

    List<Book> books = TestCreationFactory.listOf(Book.class, 1200);
    books.addAll(List.of(firstBook, secondBook, thirdBook));

    bookRepository.saveAll(books);

    BookFilterRequestDto bookFilterRequestDto = BookFilterRequestDto.builder()
        .author("%John%")
        .title("%Ring%")
        .genre("%Fantasy%")
        .price(35.00)
        .build();

    final int sortedPage = 0;
    final int sortedPageSize = 100;

    final Sort complex = Sort.by(ASC, "author").and(Sort.by(ASC, "title")).and(Sort.by(ASC, "genre"));
    final PageRequest first100AscByComplex = PageRequest.of(sortedPage, sortedPageSize, complex);
    final Page<Book> pagedResultSortAsc = bookRepository.findAll(specificationFromFilter(bookFilterRequestDto), first100AscByComplex);
    assertTrue(pagedResultSortAsc.hasContent());
    assertEquals(2, pagedResultSortAsc.getNumberOfElements());
    assertEquals(sortedPage, pagedResultSortAsc.getNumber());

    final List<Book> pagedResultSortedContent = new ArrayList<>(pagedResultSortAsc.getContent());
    assertEquals(2, pagedResultSortedContent.size());

    final Book firstBookAsc = pagedResultSortedContent.get(0);
    pagedResultSortedContent.remove(0);

    assertTrue(pagedResultSortedContent.stream().anyMatch(book -> firstBookAsc.getAuthor().compareTo(book.getAuthor()) < 0
        && firstBookAsc.getTitle().compareTo(book.getTitle()) < 0));
  }

//  @Test
//  void testComplexSpecificationQuerySecond() {
//    bookRepository.save(Book.builder()
//        .title("A Game of Thrones")
//        .author("George R.R. Martin")
//        .genre("Epic Fantasy")
//        .quantity(25)
//        .price(19.99)
//        .build()
//    );
//
//    bookRepository.save(Book.builder()
//        .title("A Clash of Kings")
//        .author("George R.R. Martin")
//        .genre("Epic Fantasy")
//        .quantity(27)
//        .price(19.99)
//        .build()
//    );
//
//    bookRepository.save(Book.builder()
//        .title("East of Eden")
//        .author("John Steinbeck")
//        .genre("Novel")
//        .quantity(32)
//        .price(27.32)
//        .build()
//    );
//
//    bookRepository.save(Book.builder()
//        .title("The Pearl")
//        .author("John Steinbeck")
//        .genre("Novel")
//        .quantity(27)
//        .price(25.99)
//        .build()
//    );
//
//    bookRepository.save(Book.builder()
//        .title("Lord of the Flies")
//        .author("William Golding")
//        .genre("Novel")
//        .quantity(27)
//        .price(21.23)
//        .build()
//    );
//
//    bookRepository.save(Book.builder()
//        .title("Lord of the Rings: The Fellowship of the Ring")
//        .author("J.R.R. Tolkien")
//        .genre("Epic Fantasy")
//        .quantity(27)
//        .price(26.50)
//        .build()
//    );
//
//    bookRepository.save(Book.builder()
//        .title("Lord of the Rings: The Return of the King")
//        .author("J.R.R. Tolkien")
//        .genre("Epic Fantasy")
//        .quantity(30)
//        .price(26.50)
//        .build()
//    );
//
//    bookRepository.save(Book.builder()
//        .title("Lord of the Rings: The Two Towers")
//        .author("J.R.R. Tolkien")
//        .genre("Epic Fantasy")
//        .quantity(30)
//        .price(26.50)
//        .build()
//    );
//
//    bookRepository.save(Book.builder()
//        .title("The Works of Lord Byron")
//        .author("Lord Byron")
//        .genre("Poetry")
//        .quantity(30)
//        .price(31.20)
//        .build()
//    );
//
//    BookFilterRequestDto bookFilterRequestDto = BookFilterRequestDto.builder()
//        .author("%John%")
//        .title("%Ring%")
//        .genre("%Fantasy%")
//        .price(35.00)
//        .build();
//
//    final int sortedPage = 0;
//    final int sortedPageSize = 100;
//
//    final Sort complex = Sort.by(ASC, "author").and(Sort.by(ASC, "title")).and(Sort.by(ASC, "genre"));
//    final PageRequest first100AscByComplex = PageRequest.of(sortedPage, sortedPageSize, complex);
//    final Page<Book> pagedResultSortAsc = bookRepository.findAll(specificationFromFilter(bookFilterRequestDto), first100AscByComplex);
//    assertTrue(pagedResultSortAsc.hasContent());
//    assertEquals(2, pagedResultSortAsc.getNumberOfElements());
//    assertEquals(sortedPage, pagedResultSortAsc.getNumber());
//
//    final List<Book> pagedResultSortedContent = new ArrayList<>(pagedResultSortAsc.getContent());
//    assertEquals(2, pagedResultSortedContent.size());
//
//    final Book firstBookAsc = pagedResultSortedContent.get(0);
//    pagedResultSortedContent.remove(0);
//
//    assertTrue(pagedResultSortedContent.stream().anyMatch(book -> firstBookAsc.getAuthor().compareTo(book.getAuthor()) < 0
//        && firstBookAsc.getTitle().compareTo(book.getTitle()) < 0));
//  }
}