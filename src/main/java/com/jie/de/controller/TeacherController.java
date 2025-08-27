package com.jie.de.controller;


import com.jie.de.model.dto.BasicInfoUpdateDTO;
import com.jie.de.model.dto.PasswordUpdateDTO;
import com.jie.de.model.dto.TeacherCourseDTO;
import com.jie.de.model.entity.Course;
import com.jie.de.service.admin.Impl.AdminServiceImpl;
import com.jie.de.service.common.impl.UserServiceImpl;
import com.jie.de.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    //获取老师信息
    @GetMapping("/information/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getUserInfo(@PathVariable(name = "userId") Long userId) throws Exception {
        return userServiceImpl.getUserInfo(userId);
    }

    //修改基础信息
    @PutMapping("/informationChange/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateUserInfo(@PathVariable(name = "userId") Long userId,@RequestBody BasicInfoUpdateDTO basicInfoUpdateDTO) {
        return userServiceImpl.changeUserInfo(userId, basicInfoUpdateDTO);
    }

    //修改密码
    @PutMapping("/passwordChange")
    @PreAuthorize("isAuthenticated()")
    public  ResponseEntity<?> updateUserPassword(@RequestBody PasswordUpdateDTO passwordUpdateDTO) {
        try {
            String newPassword = userServiceImpl.changeUserPassword(passwordUpdateDTO);
            return ResponseEntity.ok(newPassword);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //获取课程信息（半完成）
    @GetMapping("/course/{teacherId}")
    public ResponseEntity<?> getTeacherCourses(@PathVariable Long teacherId) {
        List<Course> courses = courseService.getTeacherCourses(teacherId);
        return ResponseEntity.ok(courses);
    }

}
