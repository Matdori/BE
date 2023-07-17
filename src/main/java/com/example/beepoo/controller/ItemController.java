package com.example.beepoo.controller;

import com.example.beepoo.dto.GlobalResponseDto;
import com.example.beepoo.dto.ItemRequestDto;
import com.example.beepoo.dto.ItemResponseDto;
import com.example.beepoo.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;

    // 비품 목록 조회
    @GetMapping("/item")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto<List<ItemResponseDto>> getItemList(ItemRequestDto condition, Pageable pageable) {

        return itemService.getItemList(condition, pageable);
    }

    // 비품 상세 조회
    @GetMapping("/item/detail")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto<ItemResponseDto> getItem(@RequestParam("seq") Integer seq) {
        return itemService.getItem(seq);
    }
    // 비품 등록
    @PostMapping("/item")
    @ResponseStatus(HttpStatus.CREATED)
    public GlobalResponseDto insertItemList(@RequestBody ItemRequestDto[] itemDtos) {
        return itemService.insertItemList(itemDtos);
    }

    // 비품 수정
    @PatchMapping("/item")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto updateItem(@RequestBody ItemRequestDto itemDto) {
        return itemService.updateItem(itemDto);
    }

    // 비품 삭제
    @DeleteMapping("/item")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto deleteItem(@RequestParam("seqs") List<Integer> seqs) {
        return itemService.deleteItem(seqs);
    }
}
