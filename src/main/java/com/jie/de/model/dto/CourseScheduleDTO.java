package com.jie.de.model.dto;

public class CourseScheduleDTO {

    //课程名
    private String courseName;

    //任课老师
    private String teacher;

    //上课时间（周几）
    private int weekday; // 1-7 对应周一到周日

    //上课时间（当天时间）
    private String timeSlot; // 如"第3,4节"

    //课程开始周次
    private int startWeek;

    //上课结束周次
    private int endWeek;

    //单周/双周/全部周
    private String weekType;

    //上课地点
    private String location;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
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

}
