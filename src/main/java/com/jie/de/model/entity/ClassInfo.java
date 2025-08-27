package com.jie.de.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
/**
 * 班级信息实体类
 * 存储院系、专业班级（如“网络空间安全一班”）、班级人数等基础信息
 */
@Entity
@Table(name = "class_info")
public class ClassInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //学院名称
    @Column(name = "academy_name", nullable = false, length = 100)
    @NotBlank(message = "院系名称不能为空")
    private String academyName;

    //专业班级名称
    @Column(name = "major_class_name")
    private String majorClassName;

    //班级人数
    @Column(name = "class_size")
    private Integer classSize;

    public ClassInfo() {}

    public ClassInfo(String academyName, String majorClassName, Integer classSize) {
        this.academyName = academyName;
        this.majorClassName = majorClassName;
        this.classSize = classSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    public String getMajorClassName() {
        return majorClassName;
    }

    public void setMajorClassName(String majorClassName) {
        this.majorClassName = majorClassName;
    }

    public Integer getClassSize() {
        return classSize;
    }

    public void setClassSize(Integer classSize) {
        this.classSize = classSize;
    }

    @Override
    public String toString() {
        return "ClassInfo{" +
                "id=" + id +
                ", academyName='" + academyName + '\'' +
                ", majorClassName='" + majorClassName + '\'' +
                ", classSize=" + classSize +
                '}';
    }
}
