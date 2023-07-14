package com.example.beepoo.repository;

import com.example.beepoo.dto.ItemDto;
import com.example.beepoo.dto.ItemSearchCondition;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemCustomRepository {
    List<ItemDto> getItemList(ItemSearchCondition condition, Pageable pageable);
}
