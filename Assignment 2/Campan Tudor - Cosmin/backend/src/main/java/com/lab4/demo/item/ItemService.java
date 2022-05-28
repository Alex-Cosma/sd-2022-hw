package com.lab4.demo.item;

import com.lab4.demo.item.model.Item;
import com.lab4.demo.item.model.dto.ItemDTO;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ReportServiceFactory reportServiceFactory;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    private Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
    }

    public Item findByTitle(String title) {
        return itemRepository.findByTitle(title)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + title));
    }
    public List<ItemDTO> findAll() {
        return itemRepository.findAll().stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ItemDTO> findAllByAuthor(String author) {
        return itemRepository.findAllByAuthorEquals(author).stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }
    public List<ItemDTO> findByGenre(String genre) {
        return itemRepository.findAllByGenre(genre).stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    public ItemDTO create(ItemDTO item) {
        return itemMapper.toDto(itemRepository.save(
                itemMapper.fromDto(item)
        ));
    }

    public ItemDTO edit(Long id, ItemDTO item) {
        Item actItem = findById(id);
        actItem.setTitle(item.getTitle());
        actItem.setAuthor(item.getAuthor());
        actItem.setGenre(item.getGenre());
        actItem.setPrice(item.getPrice());
        actItem.setQuantity(item.getQuantity());
        return itemMapper.toDto(
                itemRepository.save(actItem)
        );
    }
    public  void delete(Long id)
    {
        Item actItem=findById(id);
        itemRepository.delete(actItem);

    }

    public ItemDTO sell(Long id) {
        Item actItem = findById(id);
        if (actItem.getQuantity() > 0){
            actItem.setQuantity(actItem.getQuantity() - 1);
        return itemMapper.toDto(itemRepository.save(actItem));
        }
        else return null;
    }

    public List<ItemDTO> findItems0(){
        return itemRepository.findAllByQuantityEquals(0).stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    public String export(ReportType type) throws IOException {
        return reportServiceFactory.getReportService(type).export();
    }
}
