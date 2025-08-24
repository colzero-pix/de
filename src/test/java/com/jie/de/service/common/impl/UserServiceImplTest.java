package com.jie.de.service.common.impl;

import com.jie.de.model.entity.User;
import com.jie.de.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    Long userId = 111111111111L;


    @BeforeEach
    public void addTestClass() {
        User newUser = new User();
        newUser.setUserId(111111111111L);
        newUser.setUsername("Test");
        newUser.setPassword(passwordEncoder.encode("11111111"));
        newUser.setRole("student");
        userRepository.save(newUser);

        System.out.println("Test user added successfully.");
    }
    @AfterEach
    public void removeTestClass() {
        userRepository.deleteAll();
        System.out.println("Test user removed successfully.");
    }

    @Test
    void getExitUserInfo() {
        ResponseEntity<?> r = userService.getUserInfo(userId);
        assert(r.getStatusCode().is2xxSuccessful());
    }
    @Test
    void getNotExitUserInfo() {
        ResponseEntity<?> r = userService.getUserInfo(123L);
        assert(!r.getStatusCode().is2xxSuccessful());
    }

    @Test
    void changeUserInfo() {

    }

    @Test
    void changeUserPassword() {
    }

    @Test
    void findByUsername() {
    }
}