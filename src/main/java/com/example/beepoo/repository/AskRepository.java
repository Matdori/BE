package com.example.beepoo.repository;

import com.example.beepoo.entity.Ask;
import com.example.beepoo.enums.AskTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AskRepository extends JpaRepository<Ask, Integer> {
    Long countAskByType(AskTypeEnum askType);
}
