package com.jie.de.repository;

import com.jie.de.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // 根据教师ID查询课程
    List<Course> findByTeacherId(Long teacherId);

    // 根据课程名查询课程
    List<Course> findByCourseName(String courseName);

    // 根据教师姓名查询课程
    List<Course> findByTeacherName(String teacherName);

    // 根据星期几查询课程
    List<Course> findByWeekday(Integer weekday);

    // 根据地点查询课程
    List<Course> findByLocation(String location);

    // 查询教师在某时间的课程
    @Query("SELECT c FROM Course c WHERE c.teacherId = :teacherId AND c.weekday = :weekday AND c.timeSlot = :timeSlot")
    List<Course> findByTeacherAndTime(@Param("teacherId") Long teacherId,
                                      @Param("weekday") Integer weekday,
                                      @Param("timeSlot") String timeSlot);
}