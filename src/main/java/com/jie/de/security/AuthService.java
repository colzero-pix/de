package com.jie.de.security;

import com.jie.de.exception.UnauthorizedAccessException;
import com.jie.de.jwt.JwtUtil;
import com.jie.de.model.dto.LoginDTO;
import com.jie.de.model.dto.LoginResponseDTO;
import com.jie.de.model.entity.User;
import com.jie.de.service.common.impl.UserServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserServiceImpl userServiceImpl;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            UserServiceImpl userServiceImpl) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userServiceImpl = userServiceImpl;
    }

    public LoginResponseDTO authenticateUser(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsername(),
                            loginDTO.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userServiceImpl.findByUsername(loginDTO.getUsername())
                    .orElseThrow(() -> new UnauthorizedAccessException("用户未找到"));

            String jwt = jwtUtil.generateToken(loginDTO.getUsername());

            return new LoginResponseDTO(jwt, "登录成功", user.getUsername(), user.getUserId());

        }catch (BadCredentialsException e) {
            throw new UnauthorizedAccessException("用户名或者密码错误");
        }
    }

}