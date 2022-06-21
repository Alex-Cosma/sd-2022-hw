package com.app.banking.data.sql.repo;

import com.app.banking.data.sql.entity.CardStatus;
import com.app.banking.data.sql.entity.enums.ECardStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardStatusRepository extends JpaRepository<CardStatus, Integer> {

    Optional<CardStatus> findByNameLike(ECardStatus cardStatus);        //TODO test
}
