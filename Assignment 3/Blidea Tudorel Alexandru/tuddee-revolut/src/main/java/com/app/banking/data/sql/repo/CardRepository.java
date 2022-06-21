package com.app.banking.data.sql.repo;

import com.app.banking.data.sql.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Integer> {

    List<Card> findAllByOwnerId(Integer id);
    Optional<Card> findByNumberAndCvv(String number, String cvv);
    Optional<Card> findByNumber(String number);
    Optional<Card> findByIban(String iban);

}
