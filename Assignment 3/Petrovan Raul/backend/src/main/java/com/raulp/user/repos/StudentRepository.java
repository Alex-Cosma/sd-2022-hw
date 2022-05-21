package com.raulp.user.repos;

import com.raulp.user.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
    List<Student> findAllByInstructorId(Long instructorId);

    List<Student> findAllByInstructorIsNull();
}
