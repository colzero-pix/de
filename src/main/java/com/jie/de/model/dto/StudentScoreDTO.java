package com.jie.de.model.dto;

import java.math.BigDecimal;

public class StudentScoreDTO {
    private String courseName;

    private String teacherName;

    private int score;

    public StudentScoreDTO(String courseName, String teacherName, int score) {
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.score = score;
    }

    public String getCourseName() { return courseName; }

    public String getTeacherName() { return teacherName; }

    public int getScore() { return score; }
}
