package com.lab4.demo.item;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.item.model.Item;
import com.lab4.demo.item.model.dto.ItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomBoundedInt;
import static com.lab4.demo.TestCreationFactory.randomString;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testMock() {
        Item itemSaved = repository.save(Item.builder().title("whatever")
                .author(randomString())
                .genre(randomString())
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build());

        assertNotNull(itemSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(Item.builder().build());
        });
    }

    @Test
    public void testFindAll() {
        List<Item> items = TestCreationFactory.listOf(Item.class);
        repository.saveAll(items);
        List<Item> all = repository.findAll();
        assertEquals(items.size(), all.size());
    }
    @Test
    public void testFindByTitle(){
        Item item1 = Item.builder().title("title1")
                .author(randomString())
                .genre(randomString())
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();
        repository.save(item1);
        assertNotNull(repository.findByTitle("title1"));
    }
    @Test
    public void testFindByAuthor(){
        Item item1 = Item.builder().title(randomString())
                .author("auth1")
                .genre(randomString())
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();
        Item item2 = Item.builder().title(randomString())
                .author("auth1")
                .genre(randomString())
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();
        Item item3 = Item.builder().title(randomString())
                .author("auth1 2")
                .genre(randomString())
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();
        repository.save(item1);
        repository.save(item2);
        repository.save(item3);
        assertEquals(2,repository.findByAuthor("auth1").size());
    }
    @Test
    public void testFindByGenre(){
        Item item1 = Item.builder().title(randomString())
                .author(randomString())
                .genre("horror")
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();
        Item item2 = Item.builder().title(randomString())
                .author(randomString())
                .genre("horror")
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();
        Item item3 = Item.builder().title(randomString())
                .author(randomString())
                .genre("genre")
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();
        repository.save(item1);
        repository.save(item2);
        repository.save(item3);
        assertEquals(2,repository.findByGenre("horror").size());
    }
}
