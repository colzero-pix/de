package com.jie.de.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long studentId;

    @NotBlank(message = "用户名不能为空")
    @Column(name = "username", nullable = false)
    private String username;

    @NotBlank(message = "密码不能为空")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    public Student(long studentId, String username, String password, String email, String phone) {
        this.studentId = studentId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public Student() {

    }


    public long getStudentId() {
        return studentId;
    }

    public void setUserId(long studentId) {
        this.studentId = studentId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Student: \n" +
                "studentId=" + studentId + "\n" +
                "username=" + username + "\n" +
                "password=" + password + "\n" +
                "email=" + email + "\n" +
                "phone=" + phone;
    }
}
