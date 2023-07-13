package com.example.beepoo.controller;

import com.example.beepoo.dto.ItemDto;
import com.example.beepoo.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/item")
    public ResponseEntity<String> insertItemList(@RequestBody ItemDto[] itemDtos) {
        return itemService.insertItemList(itemDtos);
    }
}
