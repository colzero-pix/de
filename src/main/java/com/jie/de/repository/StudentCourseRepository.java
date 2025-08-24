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

    // 查询学生的所有课程
    @Query("SELECT c FROM Course c WHERE c.id IN " +
            "(SELECT sc.courseId FROM StudentCourse sc WHERE sc.studentId = :studentId)")
    List<Course> findCoursesByStudentId(@Param("studentId") Long studentId);
}