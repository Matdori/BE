package com.example.beepoo.controller;

import com.example.beepoo.dto.*;
import com.example.beepoo.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
@Tag(name="비품")
public class ItemController {

    private final ItemService itemService;

    // 비품 목록 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto<List<ItemResponseDto>> getItemList(ItemRequestDto condition, Pageable pageable) {

        return itemService.getItemList(condition, pageable);
    }

    // 비품 상세 조회
    @GetMapping("/detail")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto<ItemResponseDto> getItem(@RequestParam("seq") Integer seq) {
        return itemService.getItem(seq);
    }
    // 비품 등록
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GlobalResponseDto insertItemList(@RequestBody ItemRequestDto[] itemDtos) {
        return itemService.insertItemList(itemDtos);
    }

    // 비품 수정
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto updateItem(@RequestBody ItemRequestDto itemDto) {
        return itemService.updateItem(itemDto);
    }

    // 비품 삭제
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto deleteItem(@RequestParam("seqs") List<Integer> seqs) {
        return itemService.deleteItem(seqs);
    }
    
    // 비품명 중복 체크
    @GetMapping("/checkName/{name}")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto<Boolean> checkItemName(@PathVariable String name) {
        return itemService.checkItemName(name);
    }

    // 비품 타입 등록
    @PostMapping("/type")
    public GlobalResponseDto<ItemTypeResponseDto> createItemType(@RequestBody ItemTypeRequestDto itemTypeRequestDto,
                                                                 HttpServletRequest req) {
        return itemService.createItemType(itemTypeRequestDto, req);
    }

    // 비품 타입 조회
    @GetMapping("/type")
    public GlobalResponseDto<List<MapResponseDto<Long, String>>> getItemType() {
        return itemService.getItemType();
    }

    //비품 상태 조회
    @GetMapping("/status")
    public GlobalResponseDto<List<MapResponseDto<String, String>>> getItemStatus() {
        return itemService.getItemStatus();
    }

}
