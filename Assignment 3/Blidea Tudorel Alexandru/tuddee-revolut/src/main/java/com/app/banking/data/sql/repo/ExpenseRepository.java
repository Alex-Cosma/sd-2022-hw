package com.app.banking.data.sql.repo;

import com.app.banking.data.sql.entity.Expens;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expens, Long> {
}
