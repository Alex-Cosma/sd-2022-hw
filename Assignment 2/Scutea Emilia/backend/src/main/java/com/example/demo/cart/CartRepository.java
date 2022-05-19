package com.example.demo.cart;

import com.example.demo.cart.model.Cart;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

//    Cart findAllByUser_id()
}
