package com.jie.de.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "course_textbook_order")
public class CourseTextbookOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "textbook_name", nullable = false)
    private String textbookName;

    @Column(name = "order_quantity", nullable = false)
    private Integer orderQuantity;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING) // 存储枚举字符串值
    private OrderStatus status = OrderStatus.PENDING; // 默认待处理

    @Column(name = "teacher_id", nullable = false)
    private Long teacherId;

    @Column(name = "teacher_name", nullable = false)
    private String teacherName;

    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime createdTime = LocalDateTime.now();

    @Column(name = "processed_time")
    private LocalDateTime processedTime;

    // 处理状态枚举
    public enum OrderStatus {
        PENDING,   // 待处理
        APPROVED,  // 已批准
        REJECTED   // 已拒绝
    }

    // 构造方法
    public CourseTextbookOrder() {}

    public CourseTextbookOrder(Long courseId, String courseName, String textbookName,
                               Integer orderQuantity, Long teacherId, String teacherName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.textbookName = textbookName;
        this.orderQuantity = orderQuantity;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
    }

    // Getter 和 Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getTextbookName() { return textbookName; }
    public void setTextbookName(String textbookName) { this.textbookName = textbookName; }

    public Integer getOrderQuantity() { return orderQuantity; }
    public void setOrderQuantity(Integer orderQuantity) { this.orderQuantity = orderQuantity; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public LocalDateTime getCreatedTime() { return createdTime; }
    public void setCreatedTime(LocalDateTime createdTime) { this.createdTime = createdTime; }

    public LocalDateTime getProcessedTime() { return processedTime; }
    public void setProcessedTime(LocalDateTime processedTime) { this.processedTime = processedTime; }
}
