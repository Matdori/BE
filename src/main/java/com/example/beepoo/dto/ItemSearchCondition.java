package com.example.beepoo.dto;

import lombok.Data;

@Data
public class ItemSearchCondition {
    private String name;
    private String typeCode;
    private String statusCode;
    private String serial;
    private String comment;
    private String createDate;
    private String modifyDate;
    private String createUser;
    private String modifyUser;
}