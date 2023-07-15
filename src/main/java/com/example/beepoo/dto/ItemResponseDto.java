package com.example.beepoo.dto;

import com.example.beepoo.entity.Item;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ItemResponseDto {
    private Integer seq;
    private String name;
    private Integer typeCode;
    private Integer statusCode;
    private String serial;
    private String comment;
    private LocalDateTime createDate;
    private String createUser;
    private LocalDateTime modifyDate;
    private String modifyUser;

    public ItemResponseDto(Item entity){
        this.seq = entity.getSeq();
        this.name = entity.getName();
        this.typeCode = entity.getTypeCode();
        this.statusCode = entity.getStatusCode();
        this.serial = entity.getSerial();
        this.comment = entity.getComment();
        this.createDate = entity.getCreateDate();
        this.createUser = entity.getCreateUser();
        this.modifyDate = entity.getModifyDate();
        this.modifyUser = entity.getModifyUser();
    }

    @QueryProjection
    public ItemResponseDto(Integer seq, String name, Integer typeCode, Integer statusCode, String serial, String comment, LocalDateTime createDate, String createUser, LocalDateTime modifyDate, String modifyUser) {
        this.seq = seq;
        this.name = name;
        this.typeCode = typeCode;
        this.statusCode = statusCode;
        this.serial = serial;
        this.comment = comment;
        this.createDate = createDate;
        this.createUser = createUser;
        this.modifyDate = modifyDate;
        this.modifyUser = modifyUser;
    }
}