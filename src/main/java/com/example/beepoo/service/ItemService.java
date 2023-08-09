package com.example.beepoo.service;

import com.example.beepoo.dto.*;
import com.example.beepoo.entity.Item;
import com.example.beepoo.entity.ItemType;
import com.example.beepoo.enums.ItemStatusEnum;
import com.example.beepoo.exception.CustomException;
import com.example.beepoo.exception.ErrorCode;
import com.example.beepoo.repository.ItemCustomRepository;
import com.example.beepoo.repository.ItemRepository;
import com.example.beepoo.repository.ItemTypeRepository;
import com.example.beepoo.util.CurrentUser;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemCustomRepository itemCustomRepository;
    private final ItemTypeRepository itemTypeRepository;

    @Transactional
    public GlobalResponseDto<List<ItemResponseDto>> getItemList(ItemRequestDto condition, Pageable pageable) {
        Map<String, Object> itemInfo = itemCustomRepository.getItemList(condition, pageable);
        long totalCnt = (long) itemInfo.get("totalCnt");
        List<ItemResponseDto> itemList = (List<ItemResponseDto>) itemInfo.get("itemList");

        return GlobalResponseDto.ok("비품 목록 조회 성공", itemList, totalCnt);
    }

    @Transactional
    public GlobalResponseDto<ItemResponseDto> getItem(Integer seq) {
        Item itemEntity = itemRepository.findById(seq).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
        ItemResponseDto item = new ItemResponseDto(itemEntity);

        return GlobalResponseDto.ok("비품 상세 조회 성공", item);
    }

    @Transactional
    public GlobalResponseDto<String> insertItemList(ItemRequestDto[] itemDtos) {
        for (ItemRequestDto itemDto : itemDtos) {
            itemDto.setStatus(ItemStatusEnum.REGISTERED);
            ItemType itemType = itemTypeRepository
                .findById(itemDto.getTypeCode())
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_TYPE_NOT_FOUND));
            Item item = new Item(itemDto, itemType);
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
        if (itemRepository.existsByName(name)) {
            return GlobalResponseDto.ok("중복된 비품명", false);
        } else {
            return GlobalResponseDto.ok("사용 가능한 비품명", true);
        }
    }

    //Todo[07]
    @Transactional
    public GlobalResponseDto<ItemTypeResponseDto> createItemType(
        ItemTypeRequestDto itemTypeRequestDto,
        HttpServletRequest req
    ) {
        //중복확인
        if (itemTypeRepository.existsItemTypeByType(itemTypeRequestDto.getType())) {
            throw new CustomException(ErrorCode.ITEM_TYPE_ALREADY_EXIST);
        }

        ItemType parentType = null;
        Integer depth = 0;
        if (itemTypeRequestDto.getParentTypeId() != null) {
            parentType =
                itemTypeRepository
                    .findById(itemTypeRequestDto.getParentTypeId())
                    .orElseThrow(() -> new CustomException(ErrorCode.ITEM_TYPE_NOT_FOUND));
            depth = parentType.getDepth() + 1;
        }

        ItemType itemType = new ItemType(itemTypeRequestDto, parentType, depth);
        itemType.setCreated(CurrentUser.getUser(req));

        itemTypeRepository.save(itemType);
        return GlobalResponseDto.ok("비품 종류 등록 성공", new ItemTypeResponseDto(itemType));
    }

    @Transactional
    public GlobalResponseDto<Map<Long, String>> getItemType() {
        Map<Long, String> responseMap = new HashMap<>();
        List<ItemType> itemTypeList = itemTypeRepository.findAll();
        for (ItemType itemType : itemTypeList) {
            responseMap.put(itemType.getId(), itemType.getType());
        }
        return GlobalResponseDto.ok("비품 종류 조회 성공", responseMap);
    }

    @Transactional
    public GlobalResponseDto<Map<String, String>> getItemStatus() {
        Map<String, String> responseMap = new HashMap<>();
        for(ItemStatusEnum statusEnum : ItemStatusEnum.values()){
            responseMap.put(statusEnum.name(), statusEnum.status());
        }
        return GlobalResponseDto.ok("비품 상태 조회 성공", responseMap);
    }

}
