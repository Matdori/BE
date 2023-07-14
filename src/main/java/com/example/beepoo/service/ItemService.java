package com.example.beepoo.service;

import com.example.beepoo.dto.ItemDto;
import com.example.beepoo.dto.ItemSearchCondition;
import com.example.beepoo.entity.ItemEntity;
import com.example.beepoo.exception.CustomException;
import com.example.beepoo.exception.ErrorCode;
import com.example.beepoo.repository.ItemCustomRepository;
import com.example.beepoo.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemCustomRepository itemCustomRepository;

    @Transactional
    public ResponseEntity<List<ItemDto>> getItemList(ItemSearchCondition condition, Pageable pageable) {
        List<ItemDto> result = itemCustomRepository.getItemList(condition, pageable);

        return ResponseEntity.ok(result);
    }

    @Transactional
    public ResponseEntity<ItemDto> getItem(int seq) {
        // TODO(337): 사용 내역 추가 필요
        ItemEntity item = itemRepository.findById((long) seq).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        ItemDto result = new ItemDto(item);

        return ResponseEntity.ok().body(result);
    }

    @Transactional
    public ResponseEntity<String> insertItemList(ItemDto[] itemDtos) {
        for(ItemDto item : itemDtos){
            ItemEntity itemEntity = new ItemEntity(item);
            itemRepository.save(itemEntity);
        }

        return ResponseEntity.ok().body("비품 등록 성공");
    }
}
