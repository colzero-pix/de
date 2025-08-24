package com.jie.de.controller;


import com.jie.de.exception.UserIdAlreadyExistsException;
import com.jie.de.model.dto.RegisterDTO;
import com.jie.de.model.dto.CourseScheduleDTO;
import com.jie.de.model.entity.Course;
import com.jie.de.model.entity.User;
import com.jie.de.service.course.CourseService;
import com.jie.de.service.teacher.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherServiceImpl teacherServiceimpl;

    @Autowired
    private CourseService courseService;

    @PostMapping("/studentRegister")
    public ResponseEntity<?> studentRegister (@RequestBody RegisterDTO registerDTO) {
        try {
            User newUser = teacherServiceimpl.studentRegister(registerDTO);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (UserIdAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Course>> getTeacherCourses(@PathVariable Long teacherId) {
        List<Course> courses = courseService.getTeacherCourses(teacherId);
        return ResponseEntity.ok(courses);
    }

}
