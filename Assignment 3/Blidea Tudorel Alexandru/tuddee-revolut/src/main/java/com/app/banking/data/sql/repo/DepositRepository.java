package com.app.banking.data.sql.repo;

import com.app.banking.data.sql.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepositRepository extends JpaRepository<Deposit, Integer> {

    List<Deposit> findAllByOwnerId(Integer id);
}
