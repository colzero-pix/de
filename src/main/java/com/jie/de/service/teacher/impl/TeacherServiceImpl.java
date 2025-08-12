package com.jie.de.service.teacher.impl;

import com.jie.de.exception.UsernameAlreadyExistsException;
import com.jie.de.model.dto.StudentLoginDTO;
import com.jie.de.model.entity.Student;
import com.jie.de.model.entity.User;
import com.jie.de.repository.StudentRepository;
import com.jie.de.repository.UserRepository;
import com.jie.de.service.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final StudentRepository studentRepository;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public TeacherServiceImpl(StudentRepository studentRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public User studentRegister(StudentLoginDTO studentLoginDTO) {
        if(studentRepository.existsByUsername(studentLoginDTO.getUsername())) {
            throw new UsernameAlreadyExistsException("该用户名已经被注册，请更换用户名");
        }

        User newUser = new User();

        newUser.setUsername(studentLoginDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(studentLoginDTO.getPassword()));
        newUser.setRole("student");
        userRepository.save(newUser);

        return newUser;
    }


}
