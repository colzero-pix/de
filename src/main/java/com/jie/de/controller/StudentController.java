package com.jie.de.controller;

import com.jie.de.model.dto.StudentLoginDTO;
import com.jie.de.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

//    @PostMapping("/login")
//    public ResponseEntity<?> Login(@RequestBody StudentLoginDTO studentLoginDTO) {
//
//
//    }

}
