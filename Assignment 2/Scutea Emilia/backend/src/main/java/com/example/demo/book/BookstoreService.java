package com.example.demo.book;

import com.example.demo.book.model.Book;
import com.example.demo.book.model.GenreType;
import com.example.demo.book.model.dto.BookDTO;
import com.example.demo.bookreview.BookReviewMapper;
import com.example.demo.bookreview.model.BookReview;
import com.example.demo.bookreview.model.dto.BookReviewDTO;
import com.example.demo.report.ReportServiceFactory;
import com.example.demo.report.ReportType;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookstoreService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private final ReportServiceFactory reportServiceFactory;

    private final BookReviewMapper bookReviewMapper;

    public List<BookDTO> getBooksByGenre(GenreType genreType){
        return bookRepository.findAllByGenreEquals(genreType.name()).stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    public List<GenreType> getAllGenreTypes(){
       return Arrays.asList(GenreType.values().clone());
    }


    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
    }

    public boolean decreaseBookQuantity(Long id, BookDTO book) {
        Book book1 = findById(id);
        BookDTO bookDTO = bookMapper.toDto(book1);

        if (isEnoughQuantity(bookDTO, 1)) {
            Integer updateQuantity = bookDTO.getQuantity() - 1;

            bookRepository.sellBook(book.getId(), updateQuantity);
            book1 = findById(book.getId());
            return true;
        }
        return false;
    }

    public void increaseBookQuantity(Long id, BookDTO item) {
        Book item1 = findById(id);
        BookDTO bookDTO = bookMapper.toDto(item1);

        Integer updateQuantity = bookDTO.getQuantity() + 1;

        bookDTO.setQuantity(updateQuantity);

        bookRepository.save(bookMapper.fromDto(bookDTO));
    }

    private boolean isEnoughQuantity(BookDTO bookDTO, Integer quantity) {
        if (bookDTO.getQuantity() > 0) {
            return bookDTO.getQuantity() - quantity >= 0;
        }
        return false;
    }

    public List<BookDTO> searchItems(String str) {
        String string = "%" + str + "%";
        return bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike(string, string, string)
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    // CRUD OPERATIONS

    public String getApiResponse(String subject) {

        HttpResponse<String> response = Unirest.get("https://www.googleapis.com/books/v1/volumes?q=%22%22+subject:" + subject + "&key=AIzaSyAY8Odwyb8fJMqRntqFuQilqqtWRK_aCzE")
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

    public void loadItemsFromExternalApi() {

        for (GenreType type : GenreType.values()) {
            String jsonString = getApiResponse(type.name().toLowerCase()); //assign your JSON String here
            JSONObject obj = new JSONObject(jsonString);

            JSONArray arr = obj.getJSONArray("items");

            for (int i = 0; i < arr.length(); i++) {
                String title = arr.getJSONObject(i).getJSONObject("volumeInfo").getString("title");
                if(title.length() > 512){
                    title=title.substring(0,511);
                }
                String author = "";
                Integer pageCount = 0;
                String imageUrl = "";
                String description = "";
                if (arr.getJSONObject(i).getJSONObject("volumeInfo").has("authors")) {
                    author = (String) arr.getJSONObject(i).getJSONObject("volumeInfo").getJSONArray("authors").get(0);
                }
                if (arr.getJSONObject(i).getJSONObject("volumeInfo").has("pageCount")) {
                    pageCount = Integer.parseInt(arr.getJSONObject(i).getJSONObject("volumeInfo").getString("pageCount"));
                }
                if (arr.getJSONObject(i).getJSONObject("volumeInfo").has("imageLinks")) {
                    imageUrl = (String) arr.getJSONObject(i).getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail");
                }
                if (arr.getJSONObject(i).getJSONObject("volumeInfo").has("description")) {
                    description = arr.getJSONObject(i).getJSONObject("volumeInfo").getString("description");
                    if (description.length() > 3500) {
                        description = description.substring(0, 3499);
                    }
                }
                Double price;
                do {
                    price = (double) Math.round(new Random().nextDouble() * 100 * 100) / 100;
                } while (price < 20.0);

                Book item = Book.builder()
                        .title(title)
                        .author(author)
                        .imageUrl(imageUrl)
                        .description(description)
                        .pageCount(pageCount)
                        .price(price)
                        .quantity(new Random().nextInt(100))
                        .genre(type.name())
                        .build();

                bookRepository.save(item);
            }
        }
    }

    public BookDTO create(BookDTO item) {
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(item)
        ));
    }

    public BookDTO edit(Long id, BookDTO item) {
        Book actItem = findById(id);
        actItem.setTitle(item.getTitle());
        actItem.setAuthor(item.getAuthor());
        actItem.setGenre(item.getGenre());
        actItem.setQuantity(item.getQuantity());
        actItem.setPrice(item.getPrice());
        return bookMapper.toDto(
                bookRepository.save(actItem)
        );
    }

    public void delete(Long id) {
        Optional<Book> item = bookRepository.findById(id);
        item.ifPresent(bookRepository::delete);
//        return item.isPresent();
    }

    public String export(ReportType type) {
        return reportServiceFactory.getReportService(type).export();
    }
}
