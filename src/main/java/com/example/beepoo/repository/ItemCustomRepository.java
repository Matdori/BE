package com.example.beepoo.repository;

import com.example.beepoo.dto.ItemRequestDto;
import com.example.beepoo.dto.ItemResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemCustomRepository {
    List<ItemResponseDto> getItemList(ItemRequestDto condition, Pageable pageable);

    void updateItem(ItemRequestDto itemDto);
}
