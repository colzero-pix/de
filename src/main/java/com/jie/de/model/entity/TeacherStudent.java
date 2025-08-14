package com.jie.de.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "connect")
public class TeacherStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "老师id不能为空")
    @Column(name = "teacher_id")
    private Long teacherId;

    @NotBlank(message = "学生id不能为空")
    @Column(name = "student_id")
    private Long studentId;

    public TeacherStudent(long id, Long teacherId, Long studentId) {
        this.id = id;
        this.teacherId = teacherId;
        this.studentId = studentId;
    }

    public TeacherStudent() {

    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public long getStudentId() {
        return studentId;
    }
}
