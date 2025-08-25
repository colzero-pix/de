package com.jie.de.controller;

import com.jie.de.exception.UserIdAlreadyExistsException;
import com.jie.de.model.dto.AdminUserInfoDTO;
import com.jie.de.model.dto.RegisterDTO;
import com.jie.de.model.dto.ResetPasswordDTO;
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
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        adminServiceImpl.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    //修改用户信息（班级，学院）
    @PutMapping("/infoChange/{userId}")
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
    public ResponseEntity<?> updateUserPassword(@PathVariable(name = "userId") Long userId) {
        return adminServiceImpl.getUserInfo(userId);
    }



    //添加课程
    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        Course savedCourse = courseService.addCourse(course);
        return ResponseEntity.ok(savedCourse);
    }

    //删除课程
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }

    //通过课程ID获取课程信息
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
