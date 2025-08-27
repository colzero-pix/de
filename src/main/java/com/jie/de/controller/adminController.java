package com.jie.de.controller;

import com.jie.de.exception.UserIdAlreadyExistsException;
import com.jie.de.model.dto.*;
import com.jie.de.model.entity.Course;
import com.jie.de.model.entity.User;
import com.jie.de.service.admin.Impl.AdminServiceImpl;
import com.jie.de.service.common.impl.UserServiceImpl;
import com.jie.de.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;




    //注册用户
    @PostMapping("/userRegister")
    public ResponseEntity<?> userRegister (@RequestBody RegisterDTO registerDTO) {
        try {
            User newUser = adminServiceImpl.userRegister(registerDTO);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (UserIdAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //删除用户
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        adminServiceImpl.deleteUser(userId);
        return ResponseEntity.ok("用户删除成功");
    }

    //修改用户信息（班级，学院）
    @PutMapping("/infoUpdate/{userId}")
    public ResponseEntity<?> updateUserInfo(@PathVariable(name = "userId") Long userId, @RequestBody AdminUserInfoDTO adminUserInfoDTO) {
        return adminServiceImpl.changeUserInfo(userId, adminUserInfoDTO);
    }

    //重置密码
    @PutMapping("/resetPassword/{userId}")
    public ResponseEntity<?> updateUserPassword(@PathVariable(name = "userId") Long userId, @RequestBody ResetPasswordDTO resetPasswordDTO) {
        try {
            adminServiceImpl.changeUserPassword(userId, resetPasswordDTO);
            return ResponseEntity.ok("密码重置成功，新密码为：" + resetPasswordDTO.getNewPassword());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //获取用户信息
    @GetMapping("/getUserInfo/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable(name = "userId") Long userId) {
        return adminServiceImpl.getUserInfo(userId);
    }

    //获取所有用户信息
    @GetMapping("/getAllUserInfo")
    public ResponseEntity<?> getAllUserInfo() {
        return adminServiceImpl.getAllUserInfo();
    }

    //添加课程
    @PostMapping("/addCourse")
    public ResponseEntity<?> addCourse(@RequestBody CourseAddDTO courseAddDTO) {
        Course savedCourse = courseService.addCourse(courseAddDTO);
        return ResponseEntity.ok("课程添加成功");
    }

    //删除课程
    @DeleteMapping("/deleteCourse/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("课程删除成功");
    }

    //修改课程信息
    @PutMapping("/updateCourse/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody CourseUpdateDTO courseUpdateDTO) {
        Course updatedCourse = courseService.updateCourse(id, courseUpdateDTO);
        return ResponseEntity.ok("课程修改成功");
    }

    //通过课程ID获取课程信息
    @GetMapping("/courseInfo/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



}
