package com.example.demo.cart;


import com.example.demo.cart.model.Cart;
import com.example.demo.cart.model.CartDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDTO toDto(Cart cart);

    Cart fromDto(CartDTO cart);
}
