package com.example.beepoo.dto;

import com.example.beepoo.enums.ItemStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequestDto {
    private Integer seq;
    private String departmentName;
    private String name;
    private String userName;
    private Long typeCode;
    private ItemStatusEnum status;
    private String serial;
    private String comment;
    //ToDo[07] 날짜 형식 Validation
    private String startDate;
    private String endDate;
    private String createUser;
    private String modifyDate;
    private String modifyUser;
}
