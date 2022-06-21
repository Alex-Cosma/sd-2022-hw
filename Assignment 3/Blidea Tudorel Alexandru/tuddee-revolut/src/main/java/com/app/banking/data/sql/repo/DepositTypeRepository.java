package com.app.banking.data.sql.repo;

import com.app.banking.data.sql.entity.DepositType;
import com.app.banking.data.sql.entity.enums.EDepositType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepositTypeRepository extends JpaRepository<DepositType, Long> {

    Optional<DepositType> findByNameLike(EDepositType name);
}
