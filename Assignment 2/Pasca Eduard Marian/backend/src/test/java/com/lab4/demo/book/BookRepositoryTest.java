package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.user.RoleRepository;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
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

import static com.lab4.demo.user.model.ERole.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.ASC;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testMock() {
        Book itemSaved = repository.save(Book.builder().name("whatever").build());

        assertNotNull(itemSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(Book.builder().build());
        });
    }

    @Test
    public void testFindAll() {
        List<Book> items = TestCreationFactory.listOf(Book.class);
        repository.saveAll(items);
        List<Book> all = repository.findAll();
        assertEquals(items.size(), all.size());
    }

    @Test
    public void testSimpleLikeQuery() {
        final Book item1 = Book.builder()
                .author("Peter")
                .genre("Dark")
                .name("Stewie")
                .description("Something, something, something ... dark side.")
                .price(100L)
                .quantity(200L)
                .build();

        repository.save(item1);

        final List<Book> res1 = repository.findAllByNameLikeOrDescriptionLike("Stewie",
                "noooope");
        assertFalse(res1.isEmpty());
        assertEquals(1, res1.size());
        assertEquals(item1.getId(), res1.get(0).getId());

        final List<Book> res2 = repository.findAllByNameLikeOrDescriptionLike("%tew%",
                "noooope");
        assertFalse(res2.isEmpty());
        assertEquals(1, res2.size());
        assertEquals(item1.getId(), res2.get(0).getId());

        // what if we wanted sorting by default on these queries?


        // what if we need more complex queries like ... give me all the items with at least 1 excellent review?
    }

    @Test
    void testSortingLikeQuery() {
        for (int a1 = 'a'; a1 <= 'z'; a1++) {
            for (int a2 = 'a'; a2 <= 'z'; a2++) {
                for (int a3 = 'a'; a3 <= 'z'; a3++) {
                    String title = String.valueOf((char) a1) +
                            (char) a2 +
                            (char) a3;
                    repository.save(Book.builder()
                            .name(title)
                            .build());
                }
            }
        }

        final List<Book> bItemsSortedDesc = repository.findAllByNameLikeOrderByNameDesc("%b%");
        final Book firstItem = bItemsSortedDesc.get(0);
        bItemsSortedDesc.remove(0);

        assertTrue(
                bItemsSortedDesc.stream().anyMatch(item ->
                        firstItem.getName().compareTo(item.getName()) > 0
                )
        );

        // what if you also want to search ascending...?
        final List<Book> bItemsSortedAsc = repository.findAllByNameLike("%b%", Sort.by(ASC, "name"));
        final Book firstItemAsc = bItemsSortedDesc.get(0);
        bItemsSortedAsc.remove(0);

        assertTrue(
                bItemsSortedAsc.stream().anyMatch(item ->
                        firstItemAsc.getName().compareTo(item.getName()) < 0
                )
        );

        // what if now, I only want to get the first 10 such results, not 1000+ ?
    }

    @Test
    void testPaginationQuery() {
        for (int a1 = 'a'; a1 <= 'z'; a1++) {
            for (int a2 = 'a'; a2 <= 'z'; a2++) {
                for (int a3 = 'a'; a3 <= 'z'; a3++) {
                    String title = String.valueOf((char) a1) +
                            (char) a2 +
                            (char) a3;
                    repository.save(Book.builder()
                            .name(title)
                            .build());
                }
            }
        }

        final int page = 1;
        final int pageSize = 10;
        final PageRequest pageable = PageRequest.of(page, pageSize);
        final Page<Book> pagedResult = repository.findAllByNameLike("%b%", pageable);

        assertTrue(pagedResult.hasContent());
        assertEquals(pageSize, pagedResult.getNumberOfElements());
        assertEquals(page, pagedResult.getNumber());
//    assertEquals(1951, pagedResult.getTotalElements());

        // what if now we'd also want to add sorting?

        final int sortedPage = 4;
        final int sortedPageSize = 100;
        final PageRequest first100AscByName = PageRequest.of(sortedPage, sortedPageSize, Sort.by(ASC, "name"));
        final Page<Book> pagedResultSortAsc = repository.findAllByNameLike("%b%", first100AscByName);
        assertTrue(pagedResultSortAsc.hasContent());
        assertEquals(sortedPageSize, pagedResultSortAsc.getNumberOfElements());
        assertEquals(sortedPage, pagedResultSortAsc.getNumber());

        final List<Book> pagedResultSortedContent = new ArrayList<>(pagedResultSortAsc.getContent());
        assertEquals(sortedPageSize, pagedResultSortedContent.size());

        final Book firstItemAsc = pagedResultSortedContent.get(0);
        pagedResultSortedContent.remove(0);

        assertTrue(
                pagedResultSortedContent.stream().anyMatch(item ->
                        firstItemAsc.getName().compareTo(item.getName()) < 0
                )
        );
    }

    @Test
    void testSimpleSpecificationQuery() {
        for (int a1 = 'a'; a1 <= 'z'; a1++) {
            for (int a2 = 'a'; a2 <= 'z'; a2++) {
                for (int a3 = 'a'; a3 <= 'z'; a3++) {
                    String title = String.valueOf((char) a1) +
                            (char) a2 +
                            (char) a3;
                    repository.save(Book.builder()
                            .name(title)
                            .build());
                }
            }
        }

        final List<Book> items1 = repository.findAll();
        assertTrue(items1.size() > 1000);

        final String newDescription = "New and flashy";
        repository.save(
                Book.builder()
                        .name("Harry potter")
                        .author("Ion Creanga")
                        .quantity(100L)
                        .price(200L)
                        .genre("Action")
                        .description(newDescription)
                        .build()
        );

        repository.save(
                Book.builder()
                        .name("LOTR")
                        .genre("Adventure")
                        .author("JRR T")
                        .price(20000L)
                        .quantity(1L)
                        .description("Oldie goldie")
                        .build()
        );

        final List<Book> latestBooks =
                repository.findAll(
                );
        assertEquals(1, latestBooks.size());
        assertEquals(newDescription, latestBooks.get(0).getDescription());
    }

    private void buildRoles() {
        for (ERole value : values()) {
            roleRepository.save(
                    Role.builder()
                            .name(value)
                            .build()
            );
        }
    }

}
