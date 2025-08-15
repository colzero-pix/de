package com.jie.de.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class commoeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testExistUser() throws Exception {
        String json = "{ \"username\": \"yue\", \"password\": \"123456\" }";
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
        String json = "{ \"username\": \"yue\", \"password\": \"\" }";
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