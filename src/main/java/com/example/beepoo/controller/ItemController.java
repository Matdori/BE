package com.example.beepoo.controller;

import com.example.beepoo.dto.ItemDto;
import com.example.beepoo.dto.ItemSearchCondition;
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
    public ResponseEntity<List<ItemDto>> getItemList(ItemSearchCondition condition, Pageable pageable) {

        return itemService.getItemList(condition, pageable);
    }

    @GetMapping("/item/detail")
    public ResponseEntity<ItemDto> getItem(@RequestParam("seq") int seq) {
        return itemService.getItem(seq);
    }
    @PostMapping("/item")
    public ResponseEntity<String> insertItemList(@RequestBody ItemDto[] itemDtos) {
        return itemService.insertItemList(itemDtos);
    }
}
