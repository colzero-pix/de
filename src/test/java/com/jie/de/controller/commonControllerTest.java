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
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "YUE",password = "123456",roles = {"ADMIN"})
class commonControllerTest {

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
    public void testExistUser() throws Exception {
        String json = "{ \"username\": \"Test\", \"password\": \"11111111\" }";
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/login")
                .content(json.getBytes())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
    @Test
    public void testNotExistUser() throws Exception {
        String json = "{ \"username\": \"hhh\", \"password\": \"123456\" }";
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/login")
                        .content(json.getBytes())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(print());
    }
    @Test
    public void testWithEmptyPassword() throws Exception {
        String json = "{ \"username\": \"Test\", \"password\": \"\" }";
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/login")
                        .content(json.getBytes())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(print());
    }


}