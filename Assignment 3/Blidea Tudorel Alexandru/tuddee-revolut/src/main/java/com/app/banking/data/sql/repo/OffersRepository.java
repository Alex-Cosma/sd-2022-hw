package com.app.banking.data.sql.repo;

import com.app.banking.data.sql.entity.Offers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OffersRepository extends JpaRepository<Offers, Integer> {

    List<Offers> findByAvailable(boolean available);

}
