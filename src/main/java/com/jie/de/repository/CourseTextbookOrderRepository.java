package com.jie.de.repository;

import com.jie.de.model.entity.CourseTextbookOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseTextbookOrderRepository extends JpaRepository<CourseTextbookOrder, Long> {

    // 老师查询自己的订购记录
    List<CourseTextbookOrder> findByTeacherId(Long teacherId);

    // 管理员按状态查询订购记录
    List<CourseTextbookOrder> findByStatus(CourseTextbookOrder.OrderStatus status);
}
