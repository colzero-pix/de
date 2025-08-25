package com.jie.de.service.common.impl;

import com.jie.de.exception.ForbiddenException;
import com.jie.de.exception.UserNotFoundException;
import com.jie.de.model.dto.BasicInfoChangeDTO;
import com.jie.de.model.dto.PasswordChangeDTO;
import com.jie.de.model.dto.UserInfoDTO;
import com.jie.de.model.entity.User;
import com.jie.de.repository.UserRepository;
import com.jie.de.service.common.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public ResponseEntity<?> getUserInfo(Long userId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();

            User targetUser = userRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("查询ID不存在：" + userId));

            if(!targetUser.getUsername().equals(currentUsername)) {
                boolean isAdmin = authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

                if(!isAdmin) {
                    throw new ForbiddenException("无权访问该id用户信息");
                }
            }

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
    public ResponseEntity<?> changeUserInfo(Long userId, BasicInfoChangeDTO basicInfoChangeDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();

            User targetUser = userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("查询ID不存在：" + userId));

            if(!targetUser.getUsername().equals(currentUsername)) {
                boolean isAdmin = authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

                if(!isAdmin) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("无权修改该ID用户信息");
                }
            }

            targetUser.setEmail(basicInfoChangeDTO.getEmail());
            targetUser.setPhone(basicInfoChangeDTO.getPhone());

            return ResponseEntity.ok(targetUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Override
    @Transactional
    public String changeUserPassword(PasswordChangeDTO passwordChangeDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("该用户不存在" + username));

        if(!passwordEncoder.matches(passwordChangeDTO.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("当前密码输入错误");
        }

        if(!passwordChangeDTO.getConfirmPassword().equals(passwordChangeDTO.getNewPassword())) {
            throw new RuntimeException("新密码和确认密码不相同");
        }

        if(passwordEncoder.matches(passwordChangeDTO.getNewPassword(), user.getPassword())) {
            throw new RuntimeException("新密码不能与原密码相同");
        }

        user.setPassword(passwordEncoder.encode(passwordChangeDTO.getNewPassword()));
        userRepository.save(user);

        return "密码修改成功";
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
