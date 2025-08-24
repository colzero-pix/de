package com.jie.de.service.studentCourseService;

import com.jie.de.model.entity.Course;
import com.jie.de.model.entity.StudentCourse;
import com.jie.de.repository.StudentCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCourseService {

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    // 获取学生的课程表
    public List<Course> getStudentCourses(Long studentId) {
        return studentCourseRepository.findCoursesByStudentId(studentId);
    }

}