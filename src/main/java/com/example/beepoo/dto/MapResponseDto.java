package com.example.beepoo.dto;

import com.example.beepoo.entity.ItemType;
import com.example.beepoo.enums.AskTypeEnum;
import com.example.beepoo.enums.ItemStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MapResponseDto<K, V> {
    private K key;
    private V value;

    public MapResponseDto(ItemType itemType) {
        this.key = (K) itemType.getId();
        this.value = (V) itemType.getType();
    }

    public MapResponseDto(ItemStatusEnum statusEnum) {
        this.key = (K) statusEnum.name();
        this.value = (V) statusEnum.status();
    }

    public MapResponseDto(AskTypeEnum askTypeEnum) {
        this.key = (K) askTypeEnum;
        this.value = (V) askTypeEnum.type();
    }
}
