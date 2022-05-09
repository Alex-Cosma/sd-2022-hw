package com.lab4.demo.item;

import com.lab4.demo.item.model.Item;
import com.lab4.demo.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByTitle(String Title);
    List<Item> findAllByGenre(String Genre);
    List<Item> findAllByAuthorEquals(String Author);

    List<Item> findAllByQuantityEquals(int q);
}
