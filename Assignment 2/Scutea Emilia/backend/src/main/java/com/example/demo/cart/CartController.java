package com.example.demo.cart;

import com.example.demo.cart.model.Cart;
import com.example.demo.cart.model.CartDTO;
import com.example.demo.book.model.dto.BookDTO;
import com.example.demo.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.UrlMapping.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(CART)
public class CartController {

    private final CartService cartService;

    @GetMapping(GET_CART)
    public List<BookDTO> getCart(@PathVariable Long user_id){
        return cartService.getCart(user_id);
    }


    @PostMapping(CREATE_CART)
    public ResponseEntity<?> create(@PathVariable Long id, @RequestBody BookDTO book) {
        if(cartService.create(id, book)){
            return ResponseEntity.ok(new MessageResponse(String.format("Cart %d successfully created",id)));
        }
        return ResponseEntity.badRequest().body(new MessageResponse(String.format("Error: Cart %d not created",id)));

    }

    @PutMapping(DELETE_FROM_CART)
    public ResponseEntity<?> deleteFromCart(@PathVariable Long user_id, @PathVariable Long book_id, @RequestBody BookDTO book){
        if(cartService.deleteFromCart(user_id, book_id, book)){
            return ResponseEntity.ok(new MessageResponse(String.format("Book successfully deleted from cart %d",user_id)));
        }
        return ResponseEntity.badRequest().body(new MessageResponse(String.format("Error: Book not found in cart %d",user_id)));
    }

    @DeleteMapping(DELETE_CART)
    public void deleteCart(@PathVariable Long user_id){
        cartService.deleteCart(user_id);
    }

    @DeleteMapping(PLACE_ORDER)
    public void placeOrder(@PathVariable Long user_id){
        cartService.placeOrder(user_id);
    }
}
