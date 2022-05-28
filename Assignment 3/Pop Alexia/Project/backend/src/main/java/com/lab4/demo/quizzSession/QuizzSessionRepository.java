package com.lab4.demo.quizzSession;

import com.lab4.demo.quizzSession.model.QuizzSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizzSessionRepository extends JpaRepository<QuizzSession, Long> {
}
