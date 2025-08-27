package com.jie.de.service.adminTextbook;

import com.jie.de.exception.OrderNotFoundException;
import com.jie.de.model.dto.StatusUpdateDTO;
import com.jie.de.model.entity.CourseTextbookOrder;
import com.jie.de.model.entity.TextbookInventory;
import com.jie.de.repository.CourseTextbookOrderRepository;
import com.jie.de.repository.TextbookInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdminTextbookService {

    @Autowired
    private CourseTextbookOrderRepository orderRepository;

    @Autowired
    private TextbookInventoryRepository inventoryRepository;

    // 管理员查询所有订购记录（可按状态筛选）
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllOrders(CourseTextbookOrder.OrderStatus status) {
        List<CourseTextbookOrder> orders;

        if (status != null) {
            orders = orderRepository.findByStatus(status);
        } else {
            orders = orderRepository.findAll();
        }

        return orders.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("暂无订购记录") :
                ResponseEntity.ok(orders);
    }

    // 管理员更新订购状态（审核）
    @Transactional
    public ResponseEntity<?> updateOrderStatus(Long orderId, StatusUpdateDTO updateDTO) {
        try {
            // 1. 查询订购记录
            CourseTextbookOrder order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new OrderNotFoundException("订购记录ID不存在：" + orderId));

            // 2. 更新状态和处理信息
            order.setStatus(updateDTO.getStatus());
            order.setProcessedTime(LocalDateTime.now());
            orderRepository.save(order);

            // 3. 如果状态为"已批准"，更新库存
            if (CourseTextbookOrder.OrderStatus.APPROVED.equals(updateDTO.getStatus())) {
                updateTextbookInventory(order.getTextbookName(), order.getOrderQuantity());
            }

            return ResponseEntity.ok("状态更新成功：" + updateDTO.getStatus());
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("更新订购状态失败：" + e.getMessage());
        }
    }

    // 内部方法：更新教材库存
    private void updateTextbookInventory(String textbookName, Integer quantity) {
        // 查找是否已有该教材的库存记录
        Optional<TextbookInventory> existingInventory = inventoryRepository.findByTextbookName(textbookName);

        if (existingInventory.isPresent()) {
            // 已存在：更新数量（加法）
            TextbookInventory inventory = existingInventory.get();
            inventory.setTotalQuantity(inventory.getTotalQuantity() + quantity);
            inventory.setLastUpdatedTime(LocalDateTime.now());
            inventoryRepository.save(inventory);
        } else {
            // 不存在：创建新记录
            TextbookInventory newInventory = new TextbookInventory(textbookName, quantity);
            inventoryRepository.save(newInventory);
        }
    }

    // 管理员查询教材库存
    @Transactional(readOnly = true)
    public ResponseEntity<?> getTextbookInventory() {
        List<TextbookInventory> inventoryList = inventoryRepository.findAll();
        return inventoryList.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("暂无教材库存记录") :
                ResponseEntity.ok(inventoryList);
    }
}
