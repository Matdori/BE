package com.example.beepoo.controller;

import com.example.beepoo.dto.ItemRequestDto;
import com.example.beepoo.dto.ItemResponseDto;
import com.example.beepoo.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/item")
    public ResponseEntity<List<ItemResponseDto>> getItemList(ItemRequestDto condition, Pageable pageable) {

        return itemService.getItemList(condition, pageable);
    }

    @GetMapping("/item/detail")
    public ResponseEntity<ItemResponseDto> getItem(@RequestParam("seq") Integer seq) {
        return itemService.getItem(seq);
    }
    @PostMapping("/item")
    public ResponseEntity<String> insertItemList(@RequestBody ItemRequestDto[] itemDtos) {
        return itemService.insertItemList(itemDtos);
    }

    @PatchMapping("/item")
    public ResponseEntity<String> updateItem(@RequestBody ItemRequestDto itemDto) {
        return itemService.updateItem(itemDto);
    }

}
