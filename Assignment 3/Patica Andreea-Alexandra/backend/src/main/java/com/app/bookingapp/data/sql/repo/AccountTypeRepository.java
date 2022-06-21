package com.app.bookingapp.data.sql.repo;

import com.app.bookingapp.data.sql.entity.AccountType;
import com.app.bookingapp.data.sql.entity.enums.EAccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {

    Optional<AccountType> findByName(EAccountType name);  //TODO test
}
