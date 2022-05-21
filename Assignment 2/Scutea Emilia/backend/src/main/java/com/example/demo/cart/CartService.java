package com.example.demo.cart;

import com.example.demo.book.BookRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final BookMapper bookMapper;
    private final UserService userService;
    private final BookstoreService bookstoreService;
    private final BookRepository bookRepository;


    public List<BookDTO> getCart(Long user_id) {
        List<Long> book_ids = cartRepository.findAllBooksByUser_id(user_id);

        ArrayList<Book> list = new ArrayList<>();
        for (int index = 0; index < book_ids.size(); index++) {
            Long id = book_ids.get(index);
            Optional<Book> book = bookRepository.findById(id);
            Book book1 = book.get();
            if (list.isEmpty()) {
                list.add(book1);
            } else {
                list.add(index, book1);
            }
        }
        return list.stream().map(bookMapper::toDto).collect(Collectors.toList());
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

    public boolean deleteFromCart(Long user_id, Long book_id, BookDTO book) {
        // find cart by user_id and book_id
        List<Long> cart_id = cartRepository.findBookToDelete(user_id, book_id);
        Cart cart = findById(cart_id.get(0));

        // increase book quantity in db
        bookstoreService.increaseBookQuantity(book_id, book);

        // delete cart
        cartRepository.delete(cart);
        return true;
    }

    public void deleteCart(Long user_id) {
        List<Long> cartIds = cartRepository.findCartToDeleteByUser_id(user_id);
        for (Long id : cartIds) {
            Cart cart = findById(id);
            cartRepository.delete(cart);
        }
    }

    public void placeOrder(Long user_id) {
        deleteCart(user_id);
        userService.sendOrderEmail(user_id);
    }

}
