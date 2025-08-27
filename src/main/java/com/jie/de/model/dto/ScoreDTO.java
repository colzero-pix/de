package com.jie.de.model.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ScoreDTO {

    @NotBlank(message = "课程名称不能为空")
    private String courseName;

    @NotBlank(message = "老师姓名不能为空")
    private String teacherName;

    @NotBlank(message = "学生姓名不能为空")
    private String studentName;

//    @NotNull(message = "学生ID不能为空")
//    private Long studentId;

    @NotNull(message = "课程ID不能为空")
    private Long courseId;

    @DecimalMin(value = "0", message = "成绩不能小于0")
    @DecimalMax(value = "100", message = "成绩不能大于100")
    private int score;

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

//    public Long getStudentId() { return studentId; }
//    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
}
