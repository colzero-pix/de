package com.jie.de.controller;

import com.jie.de.model.dto.InfoChangeDTO;
import com.jie.de.model.dto.PasswordChangeDTO;
import com.jie.de.model.entity.Course;
import com.jie.de.security.AuthService;
import com.jie.de.service.common.impl.UserServiceImpl;
import com.jie.de.service.studentCourseService.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/information/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getUserInfo(@PathVariable(name = "userId") Long userId) throws Exception {
        return userServiceImpl.getUserInfo(userId);
    }

    @PutMapping("/informationChange/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateUserInfo(@PathVariable(name = "userId") Long userId,@RequestBody InfoChangeDTO infoChangeDTO) {
        return userServiceImpl.changeUserInfo(userId, infoChangeDTO);
    }

    @PutMapping("/passwordChange")
    @PreAuthorize("isAuthenticated()")
    public  ResponseEntity<?> updateUserPassword(@RequestBody PasswordChangeDTO passwordChangeDTO) {
        try {
            String newPassword = userServiceImpl.changeUserPassword(passwordChangeDTO);
            return ResponseEntity.ok(newPassword);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/courseInformation/{studentId}")
    public ResponseEntity<List<Course>> getStudentCourses(@PathVariable Long studentId) {
        List<Course> courses = studentCourseService.getStudentCourses(studentId);
        return ResponseEntity.ok(courses);
    }


}
