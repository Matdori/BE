package com.example.beepoo.dto;

import com.example.beepoo.entity.ItemType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ItemTypeResponseDto {

    private Integer depth;

    private String type;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long parentCode;


    public ItemTypeResponseDto(ItemType itemType) {
        this.depth = itemType.getDepth();
        this.type = itemType.getType();
        this.parentCode = itemType.getParentCode() != null ? itemType.getParentCode().getId() : null;
    }
}
