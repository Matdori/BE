package com.example.beepoo.dto;

import com.example.beepoo.entity.Item;
import com.example.beepoo.enums.ItemStatusEnum;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemResponseDto {

    private Integer seq;
    private String name;
    private Long typeCode;
    private ItemStatusEnum status;
    private String serial;
    private String comment;
    private LocalDateTime createDate;
    private String createUser;
    private LocalDateTime modifyDate;
    private String modifyUser;

    public ItemResponseDto(Item entity) {
        this.seq = entity.getSeq();
        this.name = entity.getName();
        this.typeCode = entity.getTypeCode().getId();
        this.status = entity.getStatus();
        this.serial = entity.getSerial();
        this.comment = entity.getComment();
        this.createDate = entity.getCreateDate();
        this.createUser = entity.getCreateUser();
        this.modifyDate = entity.getModifyDate();
        this.modifyUser = entity.getModifyUser();
    }

    @QueryProjection
    public ItemResponseDto(
        Integer seq,
        String name,
        Long typeCode,
        ItemStatusEnum status,
        String serial,
        String comment,
        LocalDateTime createDate,
        String createUser,
        LocalDateTime modifyDate,
        String modifyUser
    ) {
        this.seq = seq;
        this.name = name;
        this.typeCode = typeCode;
        this.status = status;
        this.serial = serial;
        this.comment = comment;
        this.createDate = createDate;
        this.createUser = createUser;
        this.modifyDate = modifyDate;
        this.modifyUser = modifyUser;
    }
}
