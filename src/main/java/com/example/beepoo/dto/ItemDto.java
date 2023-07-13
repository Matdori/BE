package com.example.beepoo.dto;

import lombok.*;

import java.util.Date;

@Data
public class ItemDto {
    private int seq;

    private String name;

    private int itemTypeCode;

    private int itemStatusCode;

    private String serial;

    private String comment;

    private Date createDate;

    private Date modifyDate;

    private String createUser;

    private String modifyUser;
}