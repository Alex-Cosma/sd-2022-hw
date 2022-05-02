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

import java.util.ArrayList;
import java.util.List;

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
    void findByAuthor() {
        List<Item> items = new ArrayList<>();
        Item item1 = Item.builder().title(randomString())
                .author("auth1")
                .genre(randomString())
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();
        Item item2 =  Item.builder().title(randomString())
                .author("auth")
                .genre(randomString())
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();
        Item item3 =  Item.builder().title(randomString())
                .author("auth1 2")
                .genre(randomString())
                .price(randomBoundedInt(100))
                .quantity(randomBoundedInt(100))
                .build();

        items.add(item1);
        items.add(item2);
        items.add(item3);

        itemRepository.save(item2);
        itemRepository.save(item1);
        itemRepository.save(item3);

        when(itemRepository.findByAuthor("auth1")).thenReturn(items);

        List<ItemDTO> all = itemService.findByAuthor("auth1");
        System.out.println(items.size()+"  "+all.size());
        Assertions.assertEquals(items.size(), all.size());
    }
}
