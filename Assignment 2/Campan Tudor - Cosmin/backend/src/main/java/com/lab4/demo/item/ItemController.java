package com.lab4.demo.item;

import com.lab4.demo.item.model.Item;
import com.lab4.demo.item.model.dto.ItemDTO;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(ITEMS)
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemDTO> allItems() {
        return itemService.findAll();
    }

//    @GetMapping
//    public Item getItemsWithTitle(@PathVariable String title) {
//        return itemService.findByTitle(title);
//    }
//
//    @GetMapping
//    public List<ItemDTO> getItemsWithAuthor(@PathVariable String author) {
//        return itemService.findAllByAuthor(author);
//    }

//    @GetMapping
//    public List<ItemDTO> getItemsWithGenre(@PathVariable String genre) {
//        return itemService.findByGenre(genre);
//    }

    @PostMapping(CREATE_ITEM)
    public ItemDTO create(@RequestBody ItemDTO item) {
        return itemService.create(item);
    }

    @DeleteMapping(ITEMS_ID_PART)
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }

    @PutMapping(ITEMS_ID_PART)
    public ItemDTO edit(@PathVariable Long id, @RequestBody ItemDTO item) {
        return itemService.edit(id, item);
    }

    @PatchMapping(ITEMS_ID_PART)
    public ItemDTO sell(@PathVariable Long id) {
        return itemService.sell(id);
    }

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type) throws IOException {
        return itemService.export(type);
    }
}
