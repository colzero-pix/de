package com.jie.de.service.teacher.impl;

import com.jie.de.exception.UserIdAlreadyExistsException;
import com.jie.de.model.dto.RegisterDTO;
import com.jie.de.model.entity.User;
import com.jie.de.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "yue", password = "11111111", roles = {"TEACHER"})
class TeacherServiceImplTest {


    @Transactional
    @Rollback()
    @Test
    void studentRegister_success() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        TeacherServiceImpl service = new TeacherServiceImpl(userRepository, passwordEncoder);

        RegisterDTO dto = new RegisterDTO();
        dto.setUserid(123L);
        dto.setUsername("teacher1");
        dto.setPassword("pass");
        dto.setRole("teacher");

        Mockito.when(userRepository.existsByUserId(dto.getUserId())).thenReturn(false);
        Mockito.when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPass");

        User user = service.studentRegister(dto);

        assertEquals(dto.getUserId(), user.getUserId());
        assertEquals(dto.getUsername(), user.getUsername());
        assertEquals("encodedPass", user.getPassword());
        assertEquals("teacher", user.getRole());
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    void studentRegister_userIdExists() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        TeacherServiceImpl service = new TeacherServiceImpl(userRepository, passwordEncoder);

        RegisterDTO dto = new RegisterDTO();
        dto.setUserid(123L);

        Mockito.when(userRepository.existsByUserId(dto.getUserId())).thenReturn(true);

        assertThrows(UserIdAlreadyExistsException.class, () -> service.studentRegister(dto));
    }
}