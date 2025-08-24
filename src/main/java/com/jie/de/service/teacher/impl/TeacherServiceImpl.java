package com.jie.de.service.teacher.impl;

import com.jie.de.exception.UserIdAlreadyExistsException;
import com.jie.de.model.dto.RegisterDTO;
import com.jie.de.model.dto.CourseScheduleDTO;
import com.jie.de.model.entity.User;
import com.jie.de.repository.UserRepository;
import com.jie.de.service.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeacherServiceImpl implements TeacherService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public TeacherServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public User studentRegister(RegisterDTO registerDTO) {
        if(userRepository.existsByUserId(registerDTO.getUserId())) {
            throw new UserIdAlreadyExistsException("该师生号已经被注册，请更换师生号");
        }

        User newUser = new User();

        newUser.setUserId(registerDTO.getUserId());
        newUser.setUsername(registerDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        newUser.setRole("student");
        userRepository.save(newUser);

        return newUser;
    }


}
