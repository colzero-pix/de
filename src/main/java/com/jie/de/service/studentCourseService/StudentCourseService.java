package com.jie.de.service.studentCourseService;

import com.jie.de.exception.UserNotFoundException;
import com.jie.de.model.entity.Course;
import com.jie.de.model.entity.StudentCourse;
import com.jie.de.model.entity.User;
import com.jie.de.repository.StudentCourseRepository;
import com.jie.de.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCourseService {

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private UserRepository userRepository;

    // 获取特定班级的所有课程
    public List<Course> getCourses() {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("错误"));

        return studentCourseRepository.findCoursesByClassNameContaining(user.getClassName());
    }



}