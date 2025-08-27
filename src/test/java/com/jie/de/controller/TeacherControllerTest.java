package com.jie.de.controller;

import com.jie.de.model.entity.User;
import com.jie.de.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "YUE",password = "123456",roles = {"ADMIN"})
class TeacherControllerTest {
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

    @Transactional
    @Rollback
    @Test
    public void testGetTeacherInfo() throws Exception {
        // 添加与认证用户一致的测试用户
        User newUser = new User();
        newUser.setUserId(111111111111L);
        newUser.setUsername("YUE");
        newUser.setPassword(passwordEncoder.encode("123456"));
        newUser.setRole("ADMIN");
        userRepository.save(newUser);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/teacher/information")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
    @Transactional
    @Rollback()
    @Test
    public void testRegisterWithRepeteUserid() throws Exception {
        String json = "{ \"username\": \"jie\", \"password\": \"qpalzm1234\", \"userId\": \"111111111111\" }";
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/teacher/studentRegister")
                        .content(json.getBytes())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(print());
    }
    @Transactional
    @Rollback()
    @Test
    public void testRegisterWithWithBlankUsername() throws Exception {
        String json = "{ \"username\": \"\", \"password\": \"qpalzm1234\", \"userId\": \"2\" }";
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/teacher/studentRegister")
                        .content(json.getBytes())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(print());
    }
    @Transactional
    @Rollback()
    @Test
    public void testRegisterWithWithBlankpassword() throws Exception {
        String json = "{ \"username\": \"jie\", \"password\": \"\", \"userId\": \"3\" }";
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/teacher/studentRegister")
                        .content(json.getBytes())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(print());
    }


    @Transactional
    @Rollback
    @Test
    public void testUpdateUserInfo() throws Exception {
        User newUser = new User();
        newUser.setUserId(111111111112L);
        newUser.setUsername("YUE");
        newUser.setPassword(passwordEncoder.encode("123456"));
        newUser.setRole("ADMIN");
        userRepository.save(newUser);

        String json = "{ \"email\": \"test@demo.com\", \"phone\": \"12345678901\" }";
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .put("/teacher/informationChange/{id}", 111111111112L)
                        .content(json.getBytes())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Transactional
    @Rollback
    @Test
    public void testUpdateUserPassword() throws Exception {
        User newUser = new User();
        newUser.setUserId(111111111111L);
        newUser.setUsername("YUE");
        newUser.setPassword(passwordEncoder.encode("123456"));
        newUser.setRole("ADMIN");
        userRepository.save(newUser);

        String json = "{ \"confirmPassword\": \"654321\", \"currentPassword\": \"123456\", \"newPassword\": \"654321\" }";
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .put("/teacher/passwordChange")
                        .content(json.getBytes())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Transactional
    @Rollback
    @Test
    public void testGetTeacherCourses() throws Exception {
        User newUser = new User();
        newUser.setUserId(111111111111L);
        newUser.setUsername("YUE");
        newUser.setPassword(passwordEncoder.encode("123456"));
        newUser.setRole("ADMIN");
        userRepository.save(newUser);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/teacher/course")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }


}