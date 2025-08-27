package com.jie.de.repository;

import com.jie.de.model.entity.TextbookInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TextbookInventoryRepository extends JpaRepository<TextbookInventory, Long> {

    // 按教材名称查询库存记录
    Optional<TextbookInventory> findByTextbookName(String textbookName);
}
