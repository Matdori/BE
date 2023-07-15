package com.example.beepoo.service;

import com.example.beepoo.dto.ItemRequestDto;
import com.example.beepoo.dto.ItemResponseDto;
import com.example.beepoo.entity.Item;
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
    public ResponseEntity<List<ItemResponseDto>> getItemList(ItemRequestDto condition, Pageable pageable) {
        List<ItemResponseDto> result = itemCustomRepository.getItemList(condition, pageable);

        return ResponseEntity.ok(result);
    }

    @Transactional
    public ResponseEntity<ItemResponseDto> getItem(int seq) {
        // TODO(337): 사용 내역 추가 필요
        Item item = itemRepository.findById((long) seq).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        ItemResponseDto result = new ItemResponseDto(item);

        return ResponseEntity.ok().body(result);
    }

    @Transactional
    public ResponseEntity<String> insertItemList(ItemRequestDto[] itemDtos) {
        for(ItemRequestDto itemDto : itemDtos){
            Item item = new Item(itemDto);
            itemRepository.save(item);
        }

        return ResponseEntity.ok().body("비품 등록 성공");
    }

    @Transactional
    public ResponseEntity<String> updateItem(ItemRequestDto itemDto) {
        itemCustomRepository.updateItem(itemDto);

        return ResponseEntity.ok().body("비품 수정 성공");
    }
}
