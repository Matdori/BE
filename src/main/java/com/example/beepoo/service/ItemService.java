package com.example.beepoo.service;

import com.example.beepoo.dto.GlobalResponseDto;
import com.example.beepoo.dto.ItemRequestDto;
import com.example.beepoo.dto.ItemResponseDto;
import com.example.beepoo.entity.Item;
import com.example.beepoo.exception.CustomException;
import com.example.beepoo.exception.ErrorCode;
import com.example.beepoo.repository.ItemCustomRepository;
import com.example.beepoo.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemCustomRepository itemCustomRepository;

    @Transactional
    public GlobalResponseDto<List<ItemResponseDto>> getItemList(ItemRequestDto condition, Pageable pageable) {
        List<ItemResponseDto> itemList = itemCustomRepository.getItemList(condition, pageable);

        return GlobalResponseDto.ok("비품 목록 조회 성공", itemList);
    }

    @Transactional
    public GlobalResponseDto<ItemResponseDto> getItem(Integer seq) {
        // TODO(337): 사용 내역 추가 필요
        Item itemEntity = itemRepository.findById(seq).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        ItemResponseDto item = new ItemResponseDto(itemEntity);

        return GlobalResponseDto.ok("비품 상세 조회 성공", item);
    }

    @Transactional
    public GlobalResponseDto<String> insertItemList(ItemRequestDto[] itemDtos) {
        for(ItemRequestDto itemDto : itemDtos){
            Item item = new Item(itemDto);
            itemRepository.save(item);
        }

        return GlobalResponseDto.ok("비품 등록 성공");
    }

    @Transactional
    public GlobalResponseDto updateItem(ItemRequestDto itemDto) {
        itemCustomRepository.updateItem(itemDto);

        return GlobalResponseDto.ok("비품 수정 성공");
    }

    @Transactional
    public GlobalResponseDto deleteItem(List<Integer> seqs) {
        itemCustomRepository.deleteItem(seqs);

        return GlobalResponseDto.ok("비품 삭제 성공");
    }

    @Transactional
    public GlobalResponseDto<Boolean> checkItemName(String name) {
        if(itemRepository.existsByName(name)){
            return GlobalResponseDto.ok("중복된 비품명", false);
        }else{
            return GlobalResponseDto.ok("사용 가능한 비품명", true);
        }
    }
}
