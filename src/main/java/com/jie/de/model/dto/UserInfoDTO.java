package com.jie.de.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserInfoDTO {

    @NotNull(message = "师生号不能为空")
    private Long userId;

    @NotBlank(message = "用户名不能为空")
    private String username;

    private String role;

    private String email;

    private String phone;

    public UserInfoDTO(Long userId, String username, String role, String email, String phone) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.email = email;
        this.phone = phone;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
