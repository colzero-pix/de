package com.jie.de.controller;

import com.jie.de.model.dto.RegisterDTO;
import com.jie.de.model.dto.ResetPasswordDTO;
import com.jie.de.model.entity.Course;
import com.jie.de.model.entity.User;
import com.jie.de.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "yue", password = "123456", roles = {"ADMIN"})
class adminControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    ObjectMapper ObjectMapper = new ObjectMapper();


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
    void testUserRegister() throws Exception {
        RegisterDTO dto = new RegisterDTO();
        dto.setUserid(222222222222L);
        dto.setUsername("NewUser");
        dto.setPassword("12345678");
        dto.setRole("student");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/userRegister")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ObjectMapper.writeValueAsString(dto)))
                        .andExpect(MockMvcResultMatchers.status().isCreated())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(222222222222L));
    }

    @Test
    void testUserRegisterDuplicate() throws Exception {
        RegisterDTO dto = new RegisterDTO();
        dto.setUserid(111111111111L);
        dto.setUsername("Test");
        dto.setPassword("11111111");
        dto.setRole("student");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/userRegister")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/deleteUser/{userId}", 111111111111L))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertFalse(userRepository.findById(111111111111L).isPresent());
    }

    @Test
    void testResetPassword() throws Exception {
        ResetPasswordDTO dto = new ResetPasswordDTO();
        dto.setNewPassword("newpassword123");
        mockMvc.perform(MockMvcRequestBuilders.put("/admin/resetPassword/{userId}", 111111111111L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("密码重置成功")));
    }

    @Test
    void testGetUserInfo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/getUserInfo/{userId}", 111111111111L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(111111111111L));
    }
    @Transactional
    @Rollback()
    @Test
    void addCourse() throws Exception {
        Course course = new Course();
        course.setCourseName("java");
        course.setCredit(3.0);
        course.setStartWeek(1);
        course.setEndWeek(20);
        course.setLocation("逸夫楼");
        course.setTeacherId(1L);
        course.setTeacherName("余胜军");
        course.setTimeSlot("1-2节");
        course.setWeekType("单周");
        course.setWeekday(202);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper.writeValueAsString(course)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseName").value("java"));
    }
    @Transactional
    @Rollback()
    @Test
    void deleteCourse() throws Exception {
        // 先添加课程
        Course course = new Course();
        course.setCourseName("java");
        course.setCredit(3.0);
        course.setStartWeek(1);
        course.setEndWeek(20);
        course.setLocation("逸夫楼");
        course.setTeacherId(1L);
        course.setTeacherName("余胜军");
        course.setTimeSlot("1-2节");
        course.setWeekType("单周");
        course.setWeekday(202);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper.writeValueAsString(course)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        // 删除课程
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/{id}", 2L))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Transactional
    @Rollback()
    @Test
    void getCourseById() throws Exception {
        Course course = new Course();
        course.setCourseName("java");
        course.setCredit(3.0);
        course.setStartWeek(1);
        course.setEndWeek(20);
        course.setLocation("逸夫楼");
        course.setTeacherId(1L);
        course.setTeacherName("余胜军");
        course.setTimeSlot("1-2节");
        course.setWeekType("单周");
        course.setWeekday(202);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper.writeValueAsString(course)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseName").value("java"));
    }
}
