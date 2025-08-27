package com.jie.de.service.course;

import com.jie.de.model.dto.CourseAddDTO;
import com.jie.de.model.dto.CourseUpdateDTO;
import com.jie.de.model.entity.Course;
import com.jie.de.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    // 获取教师的所有课程
    public List<Course> getTeacherCourses(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }

    // 获取教师某天的课程
    public List<Course> getTeacherDailyCourses(Long teacherId, Integer weekday) {
        return courseRepository.findByTeacherId(teacherId)
                .stream()
                .filter(course -> course.getWeekday().equals(weekday))
                .toList();
    }


    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    // 添加新课程
    public Course addCourse(CourseAddDTO courseAddDTO) {
        Course course = new Course();

        course.setCourseName(courseAddDTO.getCourseName());
        course.setTeacherId(courseAddDTO.getTeacherId());
        course.setTeacherName(courseAddDTO.getTeacherName());
        course.setWeekday(courseAddDTO.getWeekday());
        course.setTimeSlot(courseAddDTO.getTimeSlot());
        course.setStartWeek(courseAddDTO.getStartWeek());
        course.setEndWeek(courseAddDTO.getEndWeek());
        course.setWeekType(courseAddDTO.getWeekType());
        course.setLocation(courseAddDTO.getLocation());
        course.setCredit(courseAddDTO.getCredit());
        course.setClassName(courseAddDTO.getClassName());

        return courseRepository.save(course);
    }

    // 更新课程信息
    public Course updateCourse(Long id, CourseUpdateDTO courseUpdateDTO) {
        // 1. 查询数据库中已存在的课程
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("课程不存在，ID: " + id));

        // 2. 只更新DTO中非空的字段
        if (courseUpdateDTO.getCourseName() != null) {
            existingCourse.setCourseName(courseUpdateDTO.getCourseName());
        }
        if (courseUpdateDTO.getTeacherId() != null) {
            existingCourse.setTeacherId(courseUpdateDTO.getTeacherId());
        }
        if (courseUpdateDTO.getTeacherName() != null) {
            existingCourse.setTeacherName(courseUpdateDTO.getTeacherName());
        }
        if (courseUpdateDTO.getWeekday() != null) {
            existingCourse.setWeekday(courseUpdateDTO.getWeekday());
        }
        if (courseUpdateDTO.getTimeSlot() != null) {
            existingCourse.setTimeSlot(courseUpdateDTO.getTimeSlot());
        }
        if (courseUpdateDTO.getStartWeek() != null) {
            existingCourse.setStartWeek(courseUpdateDTO.getStartWeek());
        }
        if (courseUpdateDTO.getEndWeek() != null) {
            existingCourse.setEndWeek(courseUpdateDTO.getEndWeek());
        }
        if (courseUpdateDTO.getWeekType() != null) {
            existingCourse.setWeekType(courseUpdateDTO.getWeekType());
        }
        if (courseUpdateDTO.getLocation() != null) {
            existingCourse.setLocation(courseUpdateDTO.getLocation());
        }
        if (courseUpdateDTO.getCredit() != null) {
            existingCourse.setCredit(courseUpdateDTO.getCredit());
        }
        if (courseUpdateDTO.getClassName() != null) {
            existingCourse.setClassName(courseUpdateDTO.getClassName());
        }

        // 3. 保存更新后的课程
        return courseRepository.save(existingCourse);
    }

    // 删除课程
    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }





}