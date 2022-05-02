package com.lab4.demo.item;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.item.model.Item;
import com.lab4.demo.item.model.dto.ItemDTO;
import com.lab4.demo.report.ReportServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static com.lab4.demo.TestCreationFactory.randomBoundedInt;
import static com.lab4.demo.TestCreationFactory.randomString;
import static org.mockito.Mockito.when;

class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        itemService = new ItemService(reportServiceFactory, itemRepository, itemMapper);
    }

    @Test
    void findAll() {
        List<Item> items = TestCreationFactory.listOf(Item.class);
        when(itemRepository.findAll()).thenReturn(items);

        List<ItemDTO> all = itemService.findAll();

        Assertions.assertEquals(items.size(), all.size());
    }

    @Test
    void findAllByAuthor() {
        List<Item> items = new ArrayList<>();
        Item itemSaved = Item.builder().title(randomString())
                .genre(randomString())
                .author("Ion Creanga")
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();
        Item itemSaved2 = Item.builder().title(randomString())
                .genre(randomString())
                .author(randomString())
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();
        Item itemSaved3 = Item.builder().title(randomString())
                .genre(randomString())
                .author("Ion Creanga")
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();

        items.add(itemSaved);
        items.add(itemSaved2);
        items.add(itemSaved3);
        when(itemRepository.findAllByAuthorEquals("Ion Creanga")).thenReturn(items);


        List<ItemDTO> all = itemService.findAllByAuthor("Ion Creanga");

        System.out.println(items.size()+" "+ all.size());
        Assertions.assertEquals(items.size(), all.size());
    }

    @Test
    void sellABook()
    {

    }
}
