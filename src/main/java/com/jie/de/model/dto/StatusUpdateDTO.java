package com.jie.de.model.dto;

import com.jie.de.model.entity.CourseTextbookOrder;
import jakarta.validation.constraints.NotNull;

public class StatusUpdateDTO {

    @NotNull(message = "处理状态不能为空")
    private CourseTextbookOrder.OrderStatus status;

    public CourseTextbookOrder.OrderStatus getStatus() { return status; }
    public void setStatus(CourseTextbookOrder.OrderStatus status) { this.status = status; }

}
