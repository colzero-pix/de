package com.jie.de.service.common;

import com.jie.de.model.dto.InfoChangeDTO;
import com.jie.de.model.dto.PasswordChangeDTO;
import com.jie.de.model.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public ResponseEntity<?> getUserInfo(Long userId);

    public ResponseEntity<?> changeUserInfo(Long userId, InfoChangeDTO infoChangeDTO);

    public String changeUserPassword(PasswordChangeDTO passwordChangeDTO);
}
