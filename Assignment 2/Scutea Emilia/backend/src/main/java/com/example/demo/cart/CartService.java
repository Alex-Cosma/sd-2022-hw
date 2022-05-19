package com.example.demo.cart;

import com.example.demo.book.BookstoreService;
import com.example.demo.cart.model.Cart;
import com.example.demo.cart.model.CartDTO;
import com.example.demo.book.BookMapper;
import com.example.demo.book.model.Book;
import com.example.demo.book.model.dto.BookDTO;
import com.example.demo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final BookMapper bookMapper;
    private final UserService userService;
    private final BookstoreService bookstoreService;


    public CartDTO getCart(Long user_id){
       // Cart cart = cartRepository.findAllByUser_id(user_id);
        Cart cart = cartRepository.findById(user_id).get();
        return cartMapper.toDto(cart);
    }
    // create cart with user id and 1 book
    public boolean create(Long user_id, BookDTO book) {
        if (bookstoreService.decreaseBookQuantity(book.getId(), book)) { // if book is in stock
            Book item = bookMapper.fromDto(book);
            CartDTO cart = CartDTO.builder()
                    .user_id(user_id)
                    .items(List.of(item))
                    .build();
            cartRepository.save(cartMapper.fromDto(cart));
            return true;
        }
        return false;
    }

    public Cart findById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found: " + id));
    }

    // add 1 book, search by id
    public boolean addBook(Long id, BookDTO book) {
        if (bookstoreService.decreaseBookQuantity(book.getId(), book)) { // if book is in stock
            Cart cart = findById(id);

            // get dto
            CartDTO cartDTO = cartMapper.toDto(cart);

            // get current books
            List<Book> currentItems = cartDTO.getItems();

            // add new book to current list of books
            currentItems.add(bookMapper.fromDto(book));

            // set list of items to the new one
            cartDTO.setItems(currentItems);
            cartRepository.save(cartMapper.fromDto(cartDTO));
            return true;
        }
        return false;
    }

    public boolean deleteFromCart(Long id, BookDTO book) {
        Cart cart = findById(id);
        List<Book> items = cart.getItems();
        if (!items.isEmpty()) {
            System.out.println("enetered if");
            deleteBook(id, book);
            return true;
        }
        return false;
    }

    // delete 1 book by cart id
    public void deleteBook(Long id, BookDTO book) {
        bookstoreService.increaseBookQuantity(book.getId(), book);
        Cart cart = findById(id);
        List<Book> items = cart.getItems();

        items.remove(bookMapper.fromDto(book));
        cart.setItems(items);
        cartRepository.save(cart);
    }

    public void deleteCart(Long id) {
        Cart cart = findById(id);
        cartRepository.delete(cart);
    }

    public void placeOrder(Long id, Long user_id) {
        deleteCart(id);
        userService.sendOrderEmail(user_id);
    }

}
