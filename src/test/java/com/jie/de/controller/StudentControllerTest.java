package com.jie.de.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {
    @Autowired
    private TestRestTemplate TestRestTemplate;
    Long userId = 202205566215L;
    Long negativeId = -1L;

    @Test
    public void testGetUserInfo() {
        var response = TestRestTemplate.getForEntity("/student/studentInfo/" + userId, String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
    @Test
    public void testGetUserInfoWithNegativeId() {
        var response = TestRestTemplate.getForEntity("/student/studentInfo/" + negativeId, String.class);
        assertTrue(response.getStatusCode().is4xxClientError());
    }
}