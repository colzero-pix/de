package com.jie.de.service.common;

import com.jie.de.model.dto.BasicInfoChangeDTO;
import com.jie.de.model.dto.PasswordChangeDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public ResponseEntity<?> getUserInfo(Long userId);

    public ResponseEntity<?> changeUserInfo(Long userId, BasicInfoChangeDTO basicInfoChangeDTO);

    public String changeUserPassword(PasswordChangeDTO passwordChangeDTO);
}
