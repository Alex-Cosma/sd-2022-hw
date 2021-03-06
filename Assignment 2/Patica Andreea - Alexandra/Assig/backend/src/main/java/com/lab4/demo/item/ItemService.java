package com.lab4.demo.item;

import com.lab4.demo.item.dto.ItemDto;
import com.lab4.demo.item.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public ItemDto findById(Long id){
        return itemRepository.findById(id)
                .map(ItemDto::toDto)
                .orElseThrow(() -> new RuntimeException(format("Item with id %s not found", id)));
    }

    public List<Item> findByQuantity(Integer quantity){
        return itemRepository.findItemByQuantity(quantity);
    }

    public ItemDto addItem(ItemDto itemDto){
        Item item = Item.builder()
                .title(itemDto.getTitle())
                .author(itemDto.getAuthor())
                .price(itemDto.getPrice())
                .quantity(itemDto.getQuantity())
                .description(itemDto.getDescription().substring(0,
                        Math.min(itemDto.getDescription().length(), 1024)))
                .build();

        return ItemDto.toDto(itemRepository.save(item));
    }

    public ItemDto edit(Long id, ItemDto item) {
        ItemDto actItemDto = findById(id);
        Item actItem = Item.builder()
                .id(id)
                .title(actItemDto.getTitle())
                .author(actItemDto.getAuthor())
                .price(actItemDto.getPrice())
                .quantity(actItemDto.getQuantity())
                .description(actItemDto.getDescription())       //TODO genre
                .build();
        actItem.setTitle(item.getTitle());
        actItem.setDescription(item.getDescription());
        if(item.getPrice() >= 0){
            actItem.setPrice(item.getPrice());
        }
        actItem.setQuantity(item.getQuantity());
        return ItemDto.toDto(
                itemRepository.save(actItem)
        );
    }

    public boolean delete(Long id){
        boolean message = false;
        try{
            itemRepository.deleteById(id);
            message = true;
        }catch(IllegalArgumentException e){
            System.out.println("Item not found to delete");
        }
        return message;
    }
}
