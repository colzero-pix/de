package com.jie.de.repository;

import com.jie.de.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByStudentId(long studentId);

    Optional<Student> findByUsername(String username);

    boolean existsByUsername(String username);

}
