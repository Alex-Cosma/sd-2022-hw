package com.example.leaguecomp.item;

import com.example.leaguecomp.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findAll();

    List<Item> findAllByNameLike(String name);

    Optional<Item> findByName(String name);
}
