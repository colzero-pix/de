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

    private String className;

    private String academyName;

    public UserInfoDTO(Long userId, String username, String role, String email, String phone, String className, String academyName) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.className = className;
        this.academyName = academyName;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }
}
