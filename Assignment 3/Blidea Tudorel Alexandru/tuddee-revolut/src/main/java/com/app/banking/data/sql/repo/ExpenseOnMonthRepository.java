package com.app.banking.data.sql.repo;

import com.app.banking.data.sql.entity.ExpensesOnMonth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseOnMonthRepository extends JpaRepository<ExpensesOnMonth, Long> {
}
