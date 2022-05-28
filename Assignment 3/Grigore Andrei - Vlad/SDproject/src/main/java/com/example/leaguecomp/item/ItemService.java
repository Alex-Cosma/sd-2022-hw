package com.example.leaguecomp.item;

import com.example.leaguecomp.item.Mapper.ItemMapper;
import com.example.leaguecomp.item.dto.ItemListDTO;
import com.example.leaguecomp.item.dto.ItemMinimalDTO;
import com.example.leaguecomp.item.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public List<ItemListDTO> allItemsForList(){
        return itemRepository.findAll()
                .stream().map(itemMapper::itemListFromItem)
                .collect(Collectors.toList());
    }

    public List<ItemListDTO> allItemsByName(String name){
        return itemRepository.findAllByNameLike(name)
                .stream().map(itemMapper::itemListFromItem)
                .collect(Collectors.toList());
    }
    public void delete(Long id){
        itemRepository.deleteById(id);
    }

    public Item getById(Long id){
        return itemRepository.findById(id)
                .orElseThrow(()->new RuntimeException("no item"));
    }

}
