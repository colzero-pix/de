package com.jie.de.repository;

import com.jie.de.model.entity.Course;
import com.jie.de.model.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {


    @Query("SELECT c FROM Course c WHERE c.className LIKE %:className%")
    List<Course> findCoursesByClassNameContaining(@Param("className") String className);
}