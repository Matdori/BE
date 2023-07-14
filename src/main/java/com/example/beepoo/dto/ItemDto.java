package com.example.beepoo.dto;

import com.example.beepoo.entity.ItemEntity;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class ItemDto {
    private int seq;

    private String name;

    private int itemTypeCode;

    private int itemStatusCode;

    private String serial;

    private String comment;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private String createUser;

    private String modifyUser;

    public ItemDto(ItemEntity entity){
        this.seq = entity.getSeq();
        this.name = entity.getName();
        this.itemTypeCode = entity.getItemTypeCode();
        this.itemStatusCode = entity.getItemStatusCode();
        this.serial = entity.getSerial();
        this.comment = entity.getComment();
        this.createDate = entity.getCreateDate();
        this.modifyDate = entity.getCreateDate();
        this.createUser = entity.getCreateUser();
        this.modifyUser = entity.getModifyUser();
    }
}