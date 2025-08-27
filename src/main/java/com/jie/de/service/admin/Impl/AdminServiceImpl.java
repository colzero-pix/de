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

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    @Transactional
    public ResponseEntity<?> getAllUserInfo() {
        try {
            // 1. 查询所有用户（调用 JpaRepository 自带的 findAll()）
            List<User> allUsers = userRepository.findAll();

            // 2. 校验是否有用户数据（无数据则抛异常）
            if (allUsers.isEmpty()) {
                throw new UserNotFoundException("当前系统中暂无用户数据");
            }

            // 3. 转换为 UserInfoDTO 列表（避免暴露 User 实体的敏感字段）
            List<UserInfoDTO> allUserInfos = allUsers.stream()
                    .map(user -> new UserInfoDTO(
                            user.getUserId(),
                            user.getUsername(),
                            user.getRole(),
                            user.getEmail(),
                            user.getPhone(),
                            user.getClassName(),
                            user.getAcademyName()
                    ))
                    .collect(Collectors.toList());

            // 4. 返回成功响应（包含所有用户的 DTO 列表）
            return ResponseEntity.ok(allUserInfos);
        } catch (UserNotFoundException e) {
            // 无用户数据的异常，返回 404 + 提示信息
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // 其他未知异常（如数据库异常），返回 500 + 错误信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("获取所有用户信息失败：" + e.getMessage());
        }
    }

}
