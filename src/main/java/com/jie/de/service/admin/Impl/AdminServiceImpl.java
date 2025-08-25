package com.jie.de.service.admin.Impl;

import com.jie.de.exception.ForbiddenException;
import com.jie.de.exception.UserIdAlreadyExistsException;
import com.jie.de.exception.UserNotFoundException;
import com.jie.de.model.dto.*;
import com.jie.de.model.entity.User;
import com.jie.de.repository.UserRepository;
import com.jie.de.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminServiceImpl implements AdminService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public AdminServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User userRegister(RegisterDTO registerDTO) {
        if(userRepository.existsByUserId(registerDTO.getUserId())) {
            throw new UserIdAlreadyExistsException("该师生号已经被注册，请更换师生号");
        }

        User newUser = new User();

        newUser.setUserId(registerDTO.getUserId());
        newUser.setUsername(registerDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        newUser.setRole(registerDTO.getRole());
        newUser.setClassName(registerDTO.getClassName());
        newUser.setAcademyName(registerDTO.getAcademyName());
        userRepository.save(newUser);

        return newUser;
    }

    @Override
    @Transactional
    public void deleteUser(Long courseId) {
        userRepository.deleteById(courseId);
    }

    @Override
    @Transactional
    public ResponseEntity<?> changeUserInfo(Long userId, AdminUserInfoDTO adminUserInfoDTO) {
        try {

            User targetUser = userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("查询ID不存在：" + userId));

            targetUser.setClassName(adminUserInfoDTO.getClassName());
            targetUser.setAcademyName(adminUserInfoDTO.getAcademyName());

            return ResponseEntity.ok(targetUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void changeUserPassword(Long userId, ResetPasswordDTO resetPasswordDTO) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("该用户ID不存在" + userId));
        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public ResponseEntity<?> getUserInfo(Long userId) {
        try {
            User targetUser = userRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("查询ID不存在：" + userId));

            UserInfoDTO targetInfo = new UserInfoDTO(
                    targetUser.getUserId(),
                    targetUser.getUsername(),
                    targetUser.getRole(),
                    targetUser.getEmail(),
                    targetUser.getPhone(),
                    targetUser.getClassName(),
                    targetUser.getAcademyName()
            );

            return ResponseEntity.ok(targetInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
