package com.jie.de.model.dto;


import jakarta.validation.constraints.NotNull;

public class CourseUpdateDTO {

    @NotNull(message = "课程名不能为空")
    private String courseName;

    @NotNull(message = "教师ID不能为空")
    private Long teacherId;

    @NotNull(message = "教师名不能为空")
    private String teacherName;

    @NotNull(message = "上课周次不能为空")
    private Integer weekday;

    @NotNull(message = "时间段不能为空")
    private String timeSlot;

    @NotNull(message = "开始周不能为空")
    private Integer startWeek;

    @NotNull(message = "结束周不能为空")
    private Integer endWeek;

    @NotNull(message = "周类型不能为空")
    private String weekType;

    @NotNull(message = "上课地点不能为空")
    private String location;

    @NotNull(message = "学分不能为空")
    private Double credit;

    @NotNull(message = "上课班级不能为空")
    private String className;

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public Integer getWeekday() { return weekday; }
    public void setWeekday(Integer weekday) { this.weekday = weekday; }

    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }

    public Integer getStartWeek() { return startWeek; }
    public void setStartWeek(Integer startWeek) { this.startWeek = startWeek; }

    public Integer getEndWeek() { return endWeek; }
    public void setEndWeek(Integer endWeek) { this.endWeek = endWeek; }

    public String getWeekType() { return weekType; }
    public void setWeekType(String weekType) { this.weekType = weekType; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getCredit() { return credit; }
    public void setCredit(Double credit) { this.credit = credit; }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
