package com.example.beepoo.dto;

import com.example.beepoo.enums.AskTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AskRequestDto {
    private Integer seq;
    private Integer itemSeq;
    private AskTypeEnum askType;
    private Long askUserId;
    private String askMsg;
    private String askImg;
    private Long confirmUserId;
    private String confirmMsg;
    private String createDate;
    private String createUser;
    private String modifyDate;
    private String modifyUser;
}