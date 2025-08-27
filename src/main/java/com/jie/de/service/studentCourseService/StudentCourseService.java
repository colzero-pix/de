package com.jie.de.service.studentCourseService;

import com.jie.de.exception.UserNotFoundException;
import com.jie.de.model.entity.Course;
import com.jie.de.model.entity.StudentCourse;
import com.jie.de.model.entity.User;
import com.jie.de.repository.StudentCourseRepository;
import com.jie.de.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCourseService {

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private UserRepository userRepository;

    // 获取特定班级的所有课程
    public List<Course> getCoursesByClassName(Long userId) {

        User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("查询ID不存在：" + userId));

        return studentCourseRepository.findCoursesByClassNameContaining(user.getClassName());
    }

}