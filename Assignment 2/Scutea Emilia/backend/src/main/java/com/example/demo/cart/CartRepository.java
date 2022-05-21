package com.example.demo.cart;

import com.example.demo.cart.model.Cart;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "SELECT cart_books.books_id FROM cart_books JOIN cart ON cart.id = cart_books.cart_id WHERE cart.user_id = ?1", nativeQuery = true)
    List<Long> findAllBooksByUser_id(Long id);

    @Query(value = "SELECT cart.id FROM cart WHERE cart.user_id =?1", nativeQuery = true)
    List<Long> findCartToDeleteByUser_id(Long user_id);

    @Query(value = " SELECT cart.id, FIRST_VALUE(cart.id) over () FROM cart JOIN cart_books ON cart.id = cart_books.cart_id WHERE cart.user_id = ?1 AND cart_books.books_id = ?2", nativeQuery = true)
    List<Long> findBookToDelete(Long user_id, Long book_id);
}
