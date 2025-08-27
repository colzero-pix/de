package com.jie.de.repository;

import com.jie.de.model.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    List<Score> findByStudentId(Long studentId);

    List<Score> findByCourseId(Long courseId);

    Optional<Score> findByCourseIdAndStudentId(Long courseId, Long studentId);
}
