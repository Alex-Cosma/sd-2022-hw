package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
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
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO bookDTO) {
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(bookDTO)
        ));
    }

    public BookDTO edit(Long id, BookDTO bookDTO) {
        Book book = findById(id);
        book.setName(bookDTO.getName());
        book.setAuthor(bookDTO.getAuthor());
        book.setGenre(bookDTO.getGenre());
        book.setDescription(bookDTO.getDescription());
        book.setQuantity(bookDTO.getQuantity());
        book.setPrice(bookDTO.getPrice());
        return bookMapper.toDto(
                bookRepository.save(book)
        );
    }

    public BookDTO changeName(Long id, String newName) {
        Book book = findById(id);
        book.setName(newName);
        return bookMapper.toDto(
                bookRepository.save(book)
        );
    }

    public BookDTO get(Long id) {
        return bookMapper.toDto(findById(id));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
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
            Book book = Book.builder()
                    .author(author)
                    .name(infoObject.getString("title"))
                    .description(description)
                    .genre(genre)
                    .build();

            bookRepository.save(book);
        }
    }
}
