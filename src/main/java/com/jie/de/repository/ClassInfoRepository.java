package com.jie.de.repository;

import com.jie.de.model.entity.ClassInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassInfoRepository extends JpaRepository<ClassInfo, Long> {

    Optional<ClassInfo> findByMajorClassName(String majorClassName);

    List<ClassInfo> findByAcademyName(String academyName);

}
