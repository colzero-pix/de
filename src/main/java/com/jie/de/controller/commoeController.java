package com.jie.de.controller;

import com.jie.de.exception.UnauthorizedAccessException;
import com.jie.de.model.dto.LoginDTO;
import com.jie.de.model.dto.LoginResponseDTO;
import com.jie.de.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class commoeController {


    @Autowired
    private AuthService authService;

    //登录账户

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            LoginResponseDTO response = authService.authenticateUser(loginDTO);
            return ResponseEntity.ok(response);
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}
