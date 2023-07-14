package com.example.beepoo.service;

import com.example.beepoo.dto.ItemDto;
import com.example.beepoo.entity.ItemEntity;
import com.example.beepoo.exception.CustomException;
import com.example.beepoo.exception.ErrorCode;
import com.example.beepoo.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public ResponseEntity<String> insertItemList(ItemDto[] itemDtos) {
        for(ItemDto item : itemDtos){
            ItemEntity itemEntity = new ItemEntity(item);
            itemRepository.save(itemEntity);
        }

        return ResponseEntity.ok().body("비품 등록 성공");
    }

    @Transactional
    public ResponseEntity<ItemDto> getItem(int itemSeq) {
        // TODO(337): 사용 내역 추가 필요
        ItemEntity item = itemRepository.findById((long) itemSeq).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        ItemDto result = new ItemDto(item);

        return ResponseEntity.ok().body(result);
    }
}
