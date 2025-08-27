package com.jie.de.model.dto;

import jakarta.validation.constraints.*;

public class CourseAddDTO {

    // 课程名称：非空，长度1-50
    @NotBlank(message = "课程名称不能为空")
    @Size(max = 50, message = "课程名称长度不能超过50个字符")
    private String courseName;

    // 教师编号：非空，大于0
    @NotNull(message = "教师编号不能为空")
    @Positive(message = "教师编号必须为正整数")
    private Long teacherId;

    // 教师姓名：非空
    @NotBlank(message = "教师姓名不能为空")
    private String teacherName;

    // 星期几：非空，1-7之间
    @NotNull(message = "星期几不能为空")
    @Min(value = 1, message = "星期几必须为1-7之间的整数")
    @Max(value = 7, message = "星期几必须为1-7之间的整数")
    private Integer weekday;

    // 时间段：非空
    @NotBlank(message = "时间段不能为空")
    private String timeSlot;

    // 开始周数：非空，大于0
    @NotNull(message = "开始周数不能为空")
    @Positive(message = "开始周数必须为正整数")
    private Integer startWeek;

    // 开始周数：非空，大于0
    @NotNull(message = "结束周数不能为空")
    @Positive(message = "结束周数必须为正整数")
    private Integer endWeek;

    // 周类型：允许空（表中该字段无NOT NULL），但非空时长度不超过10
    @Size(max = 10, message = "周类型长度不能超过10个字符")
    private String weekType;

    // 上课地点：非空
    @NotBlank(message = "上课地点不能为空")
    private String location;

    // 学分：非空，大于0
    @NotNull(message = "学分不能为空")
    private Double credit;

    // 上课班级：非空
    @NotBlank(message = "上课班级不能为空")
    private String className;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Integer getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(Integer startWeek) {
        this.startWeek = startWeek;
    }

    public Integer getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(Integer endWeek) {
        this.endWeek = endWeek;
    }

    public String getWeekType() {
        return weekType;
    }

    public void setWeekType(String weekType) {
        this.weekType = weekType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "CourseInformation:" +
                "courseName='" + courseName + '\'' +
                ", teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", weekday=" + weekday +
                ", timeSlot='" + timeSlot + '\'' +
                ", startWeek=" + startWeek +
                ", endWeek=" + endWeek +
                ", weekType='" + weekType + '\'' +
                ", location='" + location + '\'' +
                ", credit=" + credit +
                ", className='" + className + '\'' +
                '}';
    }


}
