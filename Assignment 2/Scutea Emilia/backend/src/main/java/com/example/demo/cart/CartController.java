package com.example.demo.cart;

import com.example.demo.cart.model.Cart;
import com.example.demo.cart.model.CartDTO;
import com.example.demo.book.model.dto.BookDTO;
import com.example.demo.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.UrlMapping.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(CART)
public class CartController {

    private final CartService cartService;

    @GetMapping()
    public CartDTO getCart(@PathVariable Long user_id){
        return cartService.getCart(user_id);
    }


    @PostMapping(CREATE_CART)
    public ResponseEntity<?> create(@PathVariable Long id, @RequestBody BookDTO book) {
        if(cartService.create(id, book)){
            return ResponseEntity.ok(new MessageResponse(String.format("Cart %d successfully created",id)));
        }
        return ResponseEntity.badRequest().body(new MessageResponse(String.format("Error: Cart %d not created",id)));

    }

    @PutMapping(ADD_TO_CART)
    public ResponseEntity<?> addBook(@PathVariable Long id, @RequestBody BookDTO book){

        if(cartService.addBook(id, book)){
            return ResponseEntity.ok(new MessageResponse(String.format("Book successfully added to  cart %d",id)));
        }
        return ResponseEntity.badRequest().body(new MessageResponse(String.format("Error: Book not found in cart %d",id)));

    }

    @PutMapping(DELETE_FROM_CART)
    public ResponseEntity<?> deleteFromCart(@PathVariable Long id, @RequestBody BookDTO book){
        if(cartService.deleteFromCart(id, book)){
            return ResponseEntity.ok(new MessageResponse(String.format("Book successfully deleted from cart %d",id)));
        }
        return ResponseEntity.badRequest().body(new MessageResponse(String.format("Error: Book not found in cart %d",id)));
    }

    @DeleteMapping(DELETE_CART)
    public void deleteCart(@PathVariable Long id){
        cartService.deleteCart(id);
    }

    @DeleteMapping(PLACE_ORDER)
    public void placeOrder(@PathVariable Long id, @PathVariable Long user_id){
        cartService.placeOrder(id, user_id);
    }
}
