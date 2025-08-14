package com.jie.de.controller;

import com.jie.de.model.dto.LoginDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class commoeControllerTest {
    @Autowired
    private TestRestTemplate TestRestTemplate;
    LoginDTO ldto = new LoginDTO()
    {{
        setUsername("yue");
        setPassword("123456");
    }};

    LoginDTO wldto = new LoginDTO()
    {{
        setUsername("yue");
        setPassword("1234567");
    }};
    @Test
    public void testExistUser() {
        ResponseEntity<String> response = TestRestTemplate.postForEntity("/user/login", ldto, String.class);
        assert(response.getStatusCode().is2xxSuccessful());
    }
    @Test
    public void testNotExistUser() {
        ResponseEntity<String> response = TestRestTemplate.postForEntity("/user/login", wldto, String.class);
        assert(response.getStatusCode().is4xxClientError());
    }


}