package com.example.beepoo.dto;

import com.example.beepoo.entity.User;
import com.example.beepoo.enums.AskTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AskRequestDto {

    private Integer seq;
    private Integer itemSeq;
    private AskTypeEnum askType;
    private User askUser;
    private String askMsg;
    private String askImg;
    private User confirmUser;
    private String confirmMsg;
    private String createDate;
    private String createUser;
    private String modifyDate;
    private String modifyUser;
    // 비품명 검색 key
    private String itemName;
    // 비품 종류 검색 key
    private Long itemTypeCode;
    // 요청자 검색 key
    private String askUserName;
    // 처리자 검색 key
    private String confirmUserName;
}
