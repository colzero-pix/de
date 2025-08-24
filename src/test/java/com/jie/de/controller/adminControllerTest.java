package com.jie.de.controller;

import com.jie.de.model.entity.User;
import com.jie.de.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

class adminControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


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
    void addCourse() {

    }

    @Test
    void deleteCourse() {
    }

    @Test
    void getCourseById() {
    }
}