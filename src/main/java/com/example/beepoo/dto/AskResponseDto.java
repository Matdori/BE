package com.example.beepoo.dto;

import com.example.beepoo.entity.Ask;
import com.example.beepoo.entity.Item;
import com.example.beepoo.entity.User;
import com.example.beepoo.enums.AskTypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AskResponseDto {

    private Integer seq;
    private Item item;
    private AskTypeEnum type;
    private User askUser;
    private String askMsg;
    private String askImg;
    private User confirmUser;
    private String confirmMsg;
    private LocalDateTime createDate;
    private String createUser;
    private LocalDateTime modifyDate;
    private String modifyUser;

    // 요청 목록 조회
    private String itemName;
    private String itemType;
    private AskTypeEnum askType;
    private String askUserName;
    private String confirmUserName;
    private String departmentName;

    public AskResponseDto(Ask entity) {
        this.seq = entity.getSeq();
        this.item = entity.getItem();
        this.type = entity.getType();
        this.askUser = entity.getAskUser();
        this.askMsg = entity.getAskMsg();
        this.askImg = entity.getAskImg();
        this.confirmUser = entity.getConfirmUser();
        this.confirmMsg = entity.getConfirmMsg();
        this.createDate = entity.getCreateDate();
        this.createUser = entity.getCreateUser();
        this.modifyDate = entity.getModifyDate();
        this.modifyUser = entity.getModifyUser();
    }

    @QueryProjection
    public AskResponseDto(
        Integer seq,
        String itemName,
        String itemType,
        AskTypeEnum askType,
        String askUserName,
        String confirmUserName,
        String departmentName
    ) {
        this.seq = seq;
        this.itemName = itemName;
        this.itemType = itemType;
        this.askType = askType;
        this.askUserName = askUserName;
        this.confirmUserName = confirmUserName;
        this.departmentName = departmentName;
    }
}
