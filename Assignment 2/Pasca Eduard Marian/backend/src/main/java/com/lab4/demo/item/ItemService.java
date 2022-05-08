package com.lab4.demo.item;

import com.lab4.demo.item.model.Item;
import com.lab4.demo.item.model.dto.ItemDTO;
import com.lab4.demo.item.model.dto.ItemFilterRequestDto;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    private Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
    }

    public List<ItemDTO> findAll() {
        return itemRepository.findAll().stream()
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
        actItem.setName(item.getName());
        actItem.setAuthor(item.getAuthor());
        actItem.setGenre(item.getGenre());
        actItem.setDescription(item.getDescription());
        actItem.setQuantity(item.getQuantity());
        actItem.setPrice(item.getPrice());
        return itemMapper.toDto(
                itemRepository.save(actItem)
        );
    }

    public ItemDTO changeName(Long id, String newName) {
        Item item = findById(id);
        item.setName(newName);
        return itemMapper.toDto(
                itemRepository.save(item)
        );
    }

    public ItemDTO get(Long id) {
        return itemMapper.toDto(findById(id));
    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

    public Page<ItemDTO> findAllFiltered(ItemFilterRequestDto filter, Pageable pageable) {
        return itemRepository.findAll(
                ItemSpecifications.specificationsFromFilter(filter), pageable
        ).map(itemMapper::toDto);
    }

    public String getApiResponse(/*String subject*/){


        HttpResponse<String> response =  Unirest.get("https://www.googleapis.com/books/v1/volumes?q=%22%22+subject:" + /*subject +*/ "&key=AIzaSyAY8Odwyb8fJMqRntqFuQilqqtWRK_aCzE")
                .header("X-RapidAPI-Host", "google-books.p.rapidapi.com")
                .header("X-RapidAPI-Key", "5dba00051emsh1c8864c918bd7b5p10a337jsnc6a1dc72ab74")
                .asString();

        HttpClientConnectionManager poolingConnManager
                = new PoolingHttpClientConnectionManager();

        final CloseableHttpClient closeableHttpClient = HttpClients.custom()
                .setConnectionReuseStrategy(NoConnectionReuseStrategy.INSTANCE)
                .setConnectionManager(poolingConnManager)
                .build();
        return response.getBody();
    }

    public void addApiToDatabase(){
        String response  = getApiResponse();
        JSONObject responseJSONObject = new JSONObject(response);
        long size  = responseJSONObject.getInt("totalItems");
        JSONArray responseJSONArray = responseJSONObject.getJSONArray("items");
        for(int i = 0; i < responseJSONArray.length(); i++){
            JSONObject crtItem = responseJSONArray.getJSONObject(i);
            JSONObject infoObject = crtItem.getJSONObject("volumeInfo");
            String description;
            String author;
            String genre;
            if(infoObject.has("description")){
                description = infoObject.getString("description");
            }
            else{
                description = "";
            }
            if(infoObject.has("authors")){
                author = infoObject.getJSONArray("authors").getString(0);
            }
            else{
                author = "";
            }
            if(infoObject.has("categories")){
                genre = infoObject.getJSONArray("categories").getString(0);
            }
            else{
                genre = "";
            }
            Item item = Item.builder()
                    .author(author)
                    .name(infoObject.getString("title"))
                    .description(description)
                    .genre(genre)
                    .build();

            itemRepository.save(item);
        }
    }
}
