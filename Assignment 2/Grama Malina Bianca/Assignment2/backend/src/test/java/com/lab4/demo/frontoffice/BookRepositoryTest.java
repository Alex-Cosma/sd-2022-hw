package com.lab4.demo.frontoffice;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.frontoffice.model.Book;
import com.lab4.demo.user.RoleRepository;
import com.lab4.demo.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        Book bookSaved = repository.save(Book.builder().title("whatever").author("whtvr").build());

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
    public void testSimpleLikeQuery() {
        final Book item1 = Book.builder()
                .title("Game Of Thrones")
                .author("George RR Martin")
                .genre("fantasy")
                .price(100)
                .quantity(2)
                .build();

        repository.save(item1);

        final List<Book> res1 = repository.findAllByTitleLikeOrAuthorLike("Game Of Thrones", "Nah");
        assertFalse(res1.isEmpty());
        assertEquals(1, res1.size());
        assertEquals(item1.getId(), res1.get(0).getId());

        final List<Book> res2 = repository.findAllByTitleLikeOrAuthorLike("%Of%",
                "noooope");
        assertFalse(res2.isEmpty());
        assertEquals(1, res2.size());
        assertEquals(item1.getId(), res2.get(0).getId());

    }
}
//
//    @Test
//    void testSortingLikeQuery() {
//        for (int a1 = 'a'; a1 <= 'z'; a1++) {
//            for (int a2 = 'a'; a2 <= 'z'; a2++) {
//                for (int a3 = 'a'; a3 <= 'z'; a3++) {
//                    String title = String.valueOf((char) a1) +
//                            (char) a2 +
//                            (char) a3;
//                    repository.save(Item.builder()
//                            .name(title)
//                            .build());
//                }
//            }
//        }
//
//        final List<Item> bItemsSortedDesc = repository.findAllByNameLikeOrderByNameDesc("%b%");
//        final Item firstItem = bItemsSortedDesc.get(0);
//        bItemsSortedDesc.remove(0);
//
//        assertTrue(
//                bItemsSortedDesc.stream().anyMatch(item ->
//                        firstItem.getName().compareTo(item.getName()) > 0
//                )
//        );
//
//        // what if you also want to search ascending...?
//        final List<Item> bItemsSortedAsc = repository.findAllByNameLike("%b%", Sort.by(ASC, "name"));
//        final Item firstItemAsc = bItemsSortedDesc.get(0);
//        bItemsSortedAsc.remove(0);
//
//        assertTrue(
//                bItemsSortedAsc.stream().anyMatch(item ->
//                        firstItemAsc.getName().compareTo(item.getName()) < 0
//                )
//        );
//
//        // what if now, I only want to get the first 10 such results, not 1000+ ?
//    }
//
//    @Test
//    void testPaginationQuery() {
//        for (int a1 = 'a'; a1 <= 'z'; a1++) {
//            for (int a2 = 'a'; a2 <= 'z'; a2++) {
//                for (int a3 = 'a'; a3 <= 'z'; a3++) {
//                    String title = String.valueOf((char) a1) +
//                            (char) a2 +
//                            (char) a3;
//                    repository.save(Item.builder()
//                            .name(title)
//                            .build());
//                }
//            }
//        }
//
//        final int page = 1;
//        final int pageSize = 10;
//        final PageRequest pageable = PageRequest.of(page, pageSize);
//        final Page<Item> pagedResult = repository.findAllByNameLike("%b%", pageable);
//
//        assertTrue(pagedResult.hasContent());
//        assertEquals(pageSize, pagedResult.getNumberOfElements());
//        assertEquals(page, pagedResult.getNumber());
////    assertEquals(1951, pagedResult.getTotalElements());
//
//        // what if now we'd also want to add sorting?
//
//        final int sortedPage = 4;
//        final int sortedPageSize = 100;
//        final PageRequest first100AscByName = PageRequest.of(sortedPage, sortedPageSize, Sort.by(ASC, "name"));
//        final Page<Item> pagedResultSortAsc = repository.findAllByNameLike("%b%", first100AscByName);
//        assertTrue(pagedResultSortAsc.hasContent());
//        assertEquals(sortedPageSize, pagedResultSortAsc.getNumberOfElements());
//        assertEquals(sortedPage, pagedResultSortAsc.getNumber());
//
//        final List<Item> pagedResultSortedContent = new ArrayList<>(pagedResultSortAsc.getContent());
//        assertEquals(sortedPageSize, pagedResultSortedContent.size());
//
//        final Item firstItemAsc = pagedResultSortedContent.get(0);
//        pagedResultSortedContent.remove(0);
//
//        assertTrue(
//                pagedResultSortedContent.stream().anyMatch(item ->
//                        firstItemAsc.getName().compareTo(item.getName()) < 0
//                )
//        );
//    }
//
//    @Test
//    void testSimpleSpecificationQuery() {
//        for (int a1 = 'a'; a1 <= 'z'; a1++) {
//            for (int a2 = 'a'; a2 <= 'z'; a2++) {
//                for (int a3 = 'a'; a3 <= 'z'; a3++) {
//                    String title = String.valueOf((char) a1) +
//                            (char) a2 +
//                            (char) a3;
//                    repository.save(Item.builder()
//                            .name(title)
//                            .build());
//                }
//            }
//        }
//
//        final List<Item> items1 = repository.findAll(similarNames("%b%"));
//        assertTrue(items1.size() > 1000);
//
//        final String newDescription = "New and flashy";
//        repository.save(
//                Item.builder()
//                        .name("Laptop")
//                        .description(newDescription)
//                        .dateCreated(LocalDateTime.now())
//                        .build()
//        );
//
//        repository.save(
//                Item.builder()
//                        .name("Laptop")
//                        .description("Oldie goldie")
//                        .dateCreated(LocalDateTime.now().minusYears(1))
//                        .build()
//        );
//
//        final List<Item> latestLaptops =
//                repository.findAll(
//                        createdAfter(LocalDateTime.now().minusMonths(3)).and(similarNames("%Lapt%"))
//                );
//        assertEquals(1, latestLaptops.size());
//        assertEquals(newDescription, latestLaptops.get(0).getDescription());
//    }
//
//    @Test
//    void testComplicatedSpecificationQuery() {
//
//        final String qualityLaptop = "Quality laptop 1";
//        final Item excellent1 = repository.save(Item.builder()
//                .name(qualityLaptop)
//                .dateCreated(LocalDateTime.now().minusMonths(3))
//                .build());
//
//        reviewRepository.save(Review.builder()
//                .item(excellent1)
//                .text("Blanao")
//                .rating(EXCELENT)
//                .build());
//
//        reviewRepository.save(Review.builder()
//                .item(excellent1)
//                .text("SuP3r mega")
//                .rating(EXCELENT)
//                .build());
//
//
//        final String qualityMac = "Quality mac 1";
//        final Item excellent2 = repository.save(Item.builder()
//                .name(qualityMac)
//                .dateCreated(LocalDateTime.now().minusMonths(2))
//                .build());
//
//        reviewRepository.save(Review.builder()
//                .item(excellent2)
//                .text("Incredibil")
//                .rating(EXCELENT)
//                .build());
//
//        final Item average1 = repository.save(Item.builder()
//                .name("Average laptop 2")
//                .dateCreated(LocalDateTime.now().minusMonths(3))
//                .build());
//
//        reviewRepository.save(Review.builder()
//                .item(average1)
//                .text("Incredibil")
//                .rating(AVERAGE)
//                .build());
//
//        final List<Item> excellentItems = repository.findAll(onlyExcellentRated());
//        assertEquals(2, excellentItems.size());
//        assertEquals(qualityLaptop, excellentItems.get(0).getName());
//        assertEquals(qualityMac, excellentItems.get(1).getName());
//
//        final List<Item> onlyExcellentMacs = repository.findAll(onlyExcellentRated().and(similarNames("%mac%")));
//        assertEquals(1, onlyExcellentMacs.size());
//        assertEquals(qualityMac, onlyExcellentMacs.get(0).getName());
//    }
//
//    @Test
//    void testWildlyComplicatedQuery() {
//        buildRoles();
//        final User admin = userRepository.save(User.builder()
//                .username("admin")
//                .email("admin@users.com")
//                .password("stronk")
//                .roles(Set.of(roleRepository.findByName(ADMIN).get()))
//                .build());
//
//        final User customer = userRepository.save(User.builder()
//                .username("customer")
//                .email("customer@users.com")
//                .password("stronk")
//                .roles(Set.of(roleRepository.findByName(CUSTOMER).get()))
//                .build());
//
//        final User manager = userRepository.save(User.builder()
//                .username("manager")
//                .email("manager@users.com")
//                .password("stronk")
//                .roles(Set.of(roleRepository.findByName(MANAGER).get()))
//                .build());
//
//        final String laptopName = "laptop";
//        final Item laptop = repository.save(Item.builder()
//                .name(laptopName)
//                .build());
//
//        final Item mac = repository.save(Item.builder()
//                .name("mac")
//                .build());
//
//        final String webcamName = "webcam";
//        final Item webcam = repository.save(Item.builder()
//                .name(webcamName)
//                .build());
//
//        addRandomReview(laptop, admin);
//        addRandomReview(laptop, manager);
//        addRandomReview(mac, customer);
//        addRandomReview(webcam, admin);
//
//        final List<Item> onlyReviewedByAdmins = repository.findAll(withAdministratorReviews());
//        assertEquals(2, onlyReviewedByAdmins.size());
//        assertEquals(laptopName, onlyReviewedByAdmins.get(0).getName());
//    }
//
//    private void addRandomReview(Item item, User user) {
//        final Rating[] ratings = Rating.values();
//        reviewRepository.save(
//                Review.builder()
//                        .text(randomString())
//                        .rating(ratings[new Random().nextInt(ratings.length - 1)])
//                        .item(item)
//                        .user(user)
//                        .build()
//        );
//    }
//
//    private void buildRoles() {
//        for (ERole value : values()) {
//            roleRepository.save(
//                    Role.builder()
//                            .name(value)
//                            .build()
//            );
//        }
//    }

