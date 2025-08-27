package com.jie.de.service.common;

import com.jie.de.model.dto.BasicInfoUpdateDTO;
import com.jie.de.model.dto.PasswordUpdateDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public ResponseEntity<?> getUserInfo();

    public ResponseEntity<?> changeUserInfo(Long userId, BasicInfoUpdateDTO basicInfoUpdateDTO);

    public String changeUserPassword(PasswordUpdateDTO passwordUpdateDTO);
}
