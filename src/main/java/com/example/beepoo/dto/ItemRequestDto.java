package com.example.beepoo.dto;

import lombok.Getter;

@Getter
public class ItemRequestDto {
    private Integer seq;
    private String name;
    private Integer typeCode;
    private Integer statusCode;
    private String serial;
    private String comment;
    private String createDate;
    private String createUser;
    private String modifyDate;
    private String modifyUser;
}