package com.example.beepoo.repository;

import com.example.beepoo.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    boolean existsByName(String name);
    
}
