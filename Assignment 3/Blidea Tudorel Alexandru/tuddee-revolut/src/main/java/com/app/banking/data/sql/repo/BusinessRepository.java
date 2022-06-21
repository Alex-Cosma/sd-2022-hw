package com.app.banking.data.sql.repo;

import com.app.banking.data.sql.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Integer> {

    Optional<Business> findByCif(String cif);

}
