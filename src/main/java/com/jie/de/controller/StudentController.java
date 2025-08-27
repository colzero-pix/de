package com.jie.de.controller;

import com.jie.de.exception.UserNotFoundException;
import com.jie.de.model.dto.BasicInfoUpdateDTO;
import com.jie.de.model.dto.PasswordUpdateDTO;
import com.jie.de.model.entity.Course;
import com.jie.de.model.entity.User;
import com.jie.de.security.AuthService;
import com.jie.de.service.common.impl.UserServiceImpl;
import com.jie.de.service.score.ScoreService;
import com.jie.de.service.studentCourseService.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private AuthService authService;

    @Autowired
    private ScoreService scoreService;

    //获取学生信息(改)
    @GetMapping("/information")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getUserInfo() throws Exception {
        return userServiceImpl.getUserInfo();
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

    //获取课程信息（改）
    @GetMapping("/courseInformation")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Course>> getStudentCourses() {

        List<Course> courses = studentCourseService.getCourses();

        return ResponseEntity.ok(courses);
    }

    //查看自己成绩
    @GetMapping("/score")
    public ResponseEntity<?> getScoresByStudentId() {
        return scoreService.getScoresByStudentId();
    }

}
