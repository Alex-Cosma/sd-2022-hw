package com.app.banking.data.sql.repo;

import com.app.banking.data.sql.entity.CardType;
import com.app.banking.data.sql.entity.enums.ECardType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardTypeRepository extends JpaRepository<CardType, Integer> {

    Optional<CardType> findByNameLike(String name);        //TODO test

}
