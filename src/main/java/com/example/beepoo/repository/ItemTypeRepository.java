package com.example.beepoo.repository;

import com.example.beepoo.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {
    boolean existsItemTypeByType(String type);
}
