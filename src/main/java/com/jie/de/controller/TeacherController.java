package com.jie.de.controller;


import com.jie.de.exception.UsernameAlreadyExistsException;
import com.jie.de.model.dto.StudentLoginDTO;
import com.jie.de.model.entity.Student;
import com.jie.de.model.entity.User;
import com.jie.de.service.teacher.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherServiceImpl teacherServiceimpl;

    @PostMapping("/studentRegister")
    public ResponseEntity<?> studentRegister (@RequestBody StudentLoginDTO studentLoginDTO) {
        try {
            User newUser = teacherServiceimpl.studentRegister(studentLoginDTO);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
