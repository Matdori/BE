package com.example.beepoo.dto;

import com.example.beepoo.entity.Item;
import com.example.beepoo.enums.ItemStatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemResponseDto {

    private Integer seq;
    private String name;
    private String type;
    private ItemStatusEnum status;
    private String serial;
    private String comment;
    private LocalDateTime createDate;
    private String createUser;
    private LocalDateTime modifyDate;
    private String modifyUser;
    // 비품 목록 조회
    private String userName;
    private String departmentName;

    public ItemResponseDto(Item entity) {
        this.seq = entity.getSeq();
        this.name = entity.getName();
        this.type = entity.getTypeCode().getType();
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
        String type,
        ItemStatusEnum status,
        String serial,
        String comment,
        LocalDateTime createDate,
        String userName,
        String departmentName
    ) {
        this.seq = seq;
        this.name = name;
        this.type = type;
        this.status = status;
        this.serial = serial;
        this.comment = comment;
        this.createDate = createDate;
        this.userName = userName;
        this.departmentName = departmentName;
    }
}
