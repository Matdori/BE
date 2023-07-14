package com.example.beepoo.dto;

import com.example.beepoo.entity.ItemEntity;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemDto {
    private int seq;
    private String name;
    private int typeCode;
    private int statusCode;
    private String serial;
    private String comment;
    private LocalDateTime createDate;
    private String createUser;
    private LocalDateTime modifyDate;
    private String modifyUser;

    public ItemDto(ItemEntity entity){
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
    public ItemDto(int seq, String name, int typeCode, int statusCode, String serial, String comment, LocalDateTime createDate, String createUser, LocalDateTime modifyDate, String modifyUser) {
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

