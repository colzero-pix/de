package com.jie.de.service.admin;

import com.jie.de.model.dto.*;
import com.jie.de.model.entity.User;
import org.springframework.http.ResponseEntity;

public interface AdminService {

    public User userRegister(RegisterDTO registerDTO);

    public void deleteUser(Long userId);

    public void changeUserPassword(Long userId, ResetPasswordDTO resetPasswordDTO);

    public ResponseEntity<?> changeUserInfo(Long userId, AdminUserInfoDTO adminUserInfoDTO);

    public ResponseEntity<?> getUserInfo(Long userId);

    public ResponseEntity<?> getAllUserInfo();

}
