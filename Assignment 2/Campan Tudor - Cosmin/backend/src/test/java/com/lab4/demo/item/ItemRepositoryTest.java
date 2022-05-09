package com.lab4.demo.item;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.item.model.Item;
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
        Item itemSaved = repository.save(Item.builder().title(randomString())
                .genre(randomString())
                .author(randomString())
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
    public void testFindByTitle() {
        Item itemSaved = repository.save(Item.builder().title("Capra cu 3 iezi")
                .genre(randomString())
                .author(randomString())
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build());
       assertNotNull(repository.findByTitle("Capra cu 3 iezi"));
    }

    @Test
    public void testFindByAuthor() {
        Item itemSaved = repository.save(Item.builder().title(randomString())
                .genre(randomString())
                .author("Ion Creanga")
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build());
        Item itemSaved2 = repository.save(Item.builder().title(randomString())
                .genre(randomString())
                .author("Ion Creanga")
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build());
        Item itemSaved3 = repository.save(Item.builder().title(randomString())
                .genre(randomString())
                .author("Ion Creanga")
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build());
        assertEquals(3,repository.findAllByAuthorEquals("Ion Creanga").size());
    }
}
