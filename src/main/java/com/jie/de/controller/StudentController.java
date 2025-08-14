package com.jie.de.controller;

import com.jie.de.model.dto.InfoChangeDTO;
import com.jie.de.security.AuthService;
import com.jie.de.service.common.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private AuthService authService;

    @GetMapping("/studentInfo/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getUserInfo(@PathVariable(name = "userId") Long userId) throws Exception {
        return userServiceImpl.getUserInfo(userId);
    }

    @PutMapping("/studentInfoChange/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateUserInfo(@PathVariable(name = "userId") Long id,@RequestBody InfoChangeDTO infoChangeDTO) {
        return userServiceImpl.changeUserInfo(id, infoChangeDTO);
    }

}
