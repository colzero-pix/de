package com.jie.de.service.common.impl;


import com.jie.de.model.dto.BasicInfoChangeDTO;
import com.jie.de.model.dto.PasswordChangeDTO;
import com.jie.de.model.entity.User;
import com.jie.de.repository.UserRepository;
import com.jie.de.service.common.impl.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "yue", password = "11111111", roles = {"STUDENT"})
class UserServiceImplTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    Long userId = 111111111111L;

    @BeforeEach
    public void addTestClass() {
        User newUser = new User();
        newUser.setUserId(userId);
        newUser.setUsername("yue");
        newUser.setPassword(passwordEncoder.encode("11111111"));
        newUser.setRole("student");
        userRepository.save(newUser);
    }

    @AfterEach
    public void removeTestClass() {
        userRepository.deleteAll();
    }

    @Test
    void getExitUserInfo() {
        ResponseEntity<?> r = userService.getUserInfo(userId);
        assertTrue(r.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getNotExitUserInfo() {

        ResponseEntity<?> r = userService.getUserInfo(123L);
        assertFalse(r.getStatusCode().is2xxSuccessful());
    }

    @Test
    void changeUserInfo_self() {

        BasicInfoChangeDTO dto = new BasicInfoChangeDTO();
        dto.setEmail("new@test.com");
        dto.setPhone("12345678901");
        ResponseEntity<?> r = userService.changeUserInfo(userId, dto);
        assertTrue(r.getStatusCode().is2xxSuccessful());
        User user = (User) r.getBody();
        assertEquals("new@test.com", user.getEmail());
        assertEquals("12345678901", user.getPhone());
    }

    @Test
    void changeUserInfo_forbidden() {

        BasicInfoChangeDTO dto = new BasicInfoChangeDTO();
        dto.setEmail("new@test.com");
        dto.setPhone("12345678901");
        ResponseEntity<?> r = userService.changeUserInfo(userId, dto);
        assertEquals(403, r.getStatusCode().value());
    }

    @Test
    void changeUserPassword_success() {

        PasswordChangeDTO dto = new PasswordChangeDTO();
        dto.setCurrentPassword("11111111");
        dto.setNewPassword("22222222");
        dto.setConfirmPassword("22222222");
        String result = userService.changeUserPassword(dto);
        assertEquals("密码修改成功", result);
        User user = userRepository.findByUserId(userId).get();
        assertTrue(passwordEncoder.matches("22222222", user.getPassword()));
    }

    @Test
    void changeUserPassword_wrongCurrent() {

        PasswordChangeDTO dto = new PasswordChangeDTO();
        dto.setCurrentPassword("wrong");
        dto.setNewPassword("22222222");
        dto.setConfirmPassword("22222222");
        Exception ex = assertThrows(RuntimeException.class, () -> userService.changeUserPassword(dto));
        assertTrue(ex.getMessage().contains("当前密码输入错误"));
    }

    @Test
    void changeUserPassword_notMatch() {

        PasswordChangeDTO dto = new PasswordChangeDTO();
        dto.setCurrentPassword("11111111");
        dto.setNewPassword("22222222");
        dto.setConfirmPassword("33333333");
        Exception ex = assertThrows(RuntimeException.class, () -> userService.changeUserPassword(dto));
        assertTrue(ex.getMessage().contains("新密码和确认密码不相同"));
    }

    @Test
    void changeUserPassword_sameAsOld() {

        PasswordChangeDTO dto = new PasswordChangeDTO();
        dto.setCurrentPassword("11111111");
        dto.setNewPassword("11111111");
        dto.setConfirmPassword("11111111");
        Exception ex = assertThrows(RuntimeException.class, () -> userService.changeUserPassword(dto));
        assertTrue(ex.getMessage().contains("新密码不能与原密码相同"));
    }

    @Test
    void findByUsername() {
        Optional<User> user = userService.findByUsername("yue");
        assertTrue(user.isPresent());
        assertEquals(userId, user.get().getUserId());
    }
}