package com.jie.de.service.teacherTextbook;

import com.jie.de.exception.ForbiddenException;
import com.jie.de.model.dto.TextbookOrderDTO;
import com.jie.de.model.entity.Course;
import com.jie.de.model.entity.CourseTextbookOrder;
import com.jie.de.model.entity.User;
import com.jie.de.repository.CourseRepository;
import com.jie.de.repository.CourseTextbookOrderRepository;
import com.jie.de.repository.UserRepository;
import com.jie.de.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherTextbookService {

    @Autowired
    private CourseTextbookOrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    // 老师提交教材订购请求
    @Transactional
    public ResponseEntity<?> createTextbookOrder(TextbookOrderDTO orderDTO) {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User teacher= userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("该教师不存在"));

            Course course = courseRepository.findById(orderDTO.getCourseId()).orElseThrow(() -> new RuntimeException("该课程ID不存在"));

            if(!course.getTeacherName().equals(teacher.getUsername())) {
                throw new ForbiddenException("你无权订购课程ID的教材");
            }

            CourseTextbookOrder order = new CourseTextbookOrder(
                    orderDTO.getCourseId(),
                    orderDTO.getCourseName(),
                    orderDTO.getTextbookName(),
                    orderDTO.getOrderQuantity(),
                    teacher.getUserId(),
                    teacher.getUsername()
            );

            CourseTextbookOrder savedOrder = orderRepository.save(order);
            return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("提交教材订购失败：" + e.getMessage());
        }
    }

    // 老师查询自己的订购记录
    @Transactional(readOnly = true)
    public ResponseEntity<?> getTeacherOrders() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User teacher= userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("该教师不存在"));

        List<CourseTextbookOrder> orders = orderRepository.findByTeacherId(teacher.getUserId());
        return orders.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("暂无教材订购记录") :
                ResponseEntity.ok(orders);
    }
}
