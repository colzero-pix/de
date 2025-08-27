package com.jie.de.controller;


import com.jie.de.model.dto.*;
import com.jie.de.model.entity.Course;
import com.jie.de.repository.CourseTextbookOrderRepository;
import com.jie.de.service.admin.Impl.AdminServiceImpl;
import com.jie.de.service.common.impl.UserServiceImpl;
import com.jie.de.service.course.CourseService;
import com.jie.de.service.score.ScoreService;
import com.jie.de.service.teacherTextbook.TeacherTextbookService;
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

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private TeacherTextbookService teacherTextbookService;

    //获取老师信息（改）
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

    //获取课程信息(改)
    @GetMapping("/course")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getTeacherCourses() {
        List<Course> courses = courseService.getTeacherCourses();
        return ResponseEntity.ok(courses);
    }

    // 新增成绩
    @PostMapping("/addScore")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addScore(@RequestBody ScoreDTO scoreDTO) {
        return scoreService.addScore(scoreDTO);
    }

    // 老师查询某课程的成绩
    @GetMapping("/course/{courseId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getScoresByCourseId(@PathVariable Long courseId) {
        return scoreService.getScoresByCourseId(courseId);
    }


    // 提交教材订购请求
    @PostMapping("/order")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createOrder(
            @RequestBody TextbookOrderDTO orderDTO
    ) {
        return teacherTextbookService.createTextbookOrder(orderDTO);
    }

    // 查询自己的订购记录
    @GetMapping("/orders")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMyOrders() {
        return teacherTextbookService.getTeacherOrders();
    }

}
