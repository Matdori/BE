package com.example.beepoo.dto;

import com.example.beepoo.enums.ItemStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequestDto {

    private Integer seq;
    private String name;
    private Integer typeCode;
    private ItemStatusEnum status;
    private String serial;
    private String comment;
    private String createDate;
    private String createUser;
    private String modifyDate;
    private String modifyUser;
}
