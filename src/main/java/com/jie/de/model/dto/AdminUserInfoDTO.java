package com.jie.de.model.dto;

public class AdminUserInfoDTO {

    private String className;

    private String academyName;

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    public String getAcademyName() {
        return academyName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
