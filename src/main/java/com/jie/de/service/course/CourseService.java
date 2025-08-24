package com.jie.de.service.course;

import com.jie.de.model.entity.Course;
import com.jie.de.repository.CourseRepository;
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
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    // 更新课程信息
    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }

    // 删除课程
    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }
}