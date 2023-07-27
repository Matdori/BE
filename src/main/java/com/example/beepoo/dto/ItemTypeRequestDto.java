package com.example.beepoo.dto;

import lombok.Getter;

@Getter
public class ItemTypeRequestDto {

    private Long typeId;

    private String type;

    private Long parentTypeId;
}
