package com.jie.de.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ClassInfoDTO {
    private Long id;

    private String academyName;

    private String majorClassName;

    private Integer classSize;

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
                "academyName='" + academyName + '\'' +
                ", majorClassName='" + majorClassName + '\'' +
                ", classSize=" + classSize +
                '}';
    }

}
