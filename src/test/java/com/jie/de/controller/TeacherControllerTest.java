package com.jie.de.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TeacherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Transactional
    @Rollback()
    @Test
    public void testRegisterStudent() throws Exception {
        String json = "{ \"username\": \"jie\", \"password\": \"qpalzm1234\", \"userId\": \"202205566299\" }";
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                .post("/teacher/studentRegister")
                .content(json.getBytes())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(print());
    }
    @Transactional
    @Rollback()
    @Test
    public void testRegisterWithRepeteUseridStudent() throws Exception {
        String json = "{ \"username\": \"jie\", \"password\": \"qpalzm1234\", \"userId\": \"202205566215\" }";
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/teacher/studentRegister")
                        .content(json.getBytes())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(print());
    }


}