package com.lab4.demo.investment;

import com.lab4.demo.investment.model.Investment;
import com.lab4.demo.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    Optional<Investment> findById(Long id);
    List<Investment> findAllByUserId(Long userId);
}
