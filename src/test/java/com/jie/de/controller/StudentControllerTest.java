package com.jie.de.controller;

import com.jie.de.model.dto.PasswordUpdateDTO;
import com.jie.de.model.entity.User;
import com.jie.de.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc

@WithMockUser(username = "YUE",password = "123456",roles = {"student"})
class StudentControllerTest {
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
        newUser.setUsername("YUE");
        newUser.setPassword(passwordEncoder.encode("123456"));
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
    public void testGetUserInfo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/information")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
    @Test
    public void testUpdateUserInfo() throws Exception {
        // 构造基础信息修改 DTO
        String basicInfoUpdateJson = "{ \"nickname\": \"新昵称\", \"email\": \"test@example.com\" }";
        Long userId = 111111111111L;

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student/informationChange/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(basicInfoUpdateJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
    @Test
    public void testUpdateUserPassword() throws Exception {
        // 构造密码修改 DTO
        String passwordUpdateJson = "{ \"confirmPassword\": \"654321\", \"currentPassword\": \"123456\", \"newPassword\": \"654321\" }";
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student/passwordChange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(passwordUpdateJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }


}
