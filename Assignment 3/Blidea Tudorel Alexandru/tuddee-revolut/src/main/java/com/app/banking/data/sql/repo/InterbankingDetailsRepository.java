package com.app.banking.data.sql.repo;

import com.app.banking.data.sql.entity.InterbankingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterbankingDetailsRepository extends JpaRepository<InterbankingDetail, Long> {

}
