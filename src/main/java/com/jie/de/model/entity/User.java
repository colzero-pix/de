package com.jie.de.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "师生号不能为空")
    @Column(name = "user_id")
    private Long userId;

    @NotBlank(message = "用户名不能为空")
    @Column(name = "username", nullable = false)
    private String username;

    @NotBlank(message = "密码不能为空")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "class_name")
    private String className;

    @Column(name = "academy_name")
    private String academyName;

    public User(long id, long userId, String username, String password, String role, String email, String phone, String className, String academyName) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.className = className;
        this.academyName= academyName;
    }

    public User() {

    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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


    @Override
    public String toString() {
        return "User: \n" +
                "UserId=" + userId + "\n" +
                "username=" + username + "\n" +
                "password=" + password + "\n" +
                "role=" + role + "\n" +
                "email=" + email + "\n" +
                "phone=" + phone + "\n" +
                "class=" + className + "\n" +
                "academy=" + academyName;
    }
}
