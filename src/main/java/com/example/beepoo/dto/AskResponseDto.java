package com.example.beepoo.dto;

import com.example.beepoo.entity.Ask;
import com.example.beepoo.entity.Item;
import com.example.beepoo.entity.User;
import com.example.beepoo.enums.AskTypeEnum;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
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

    public AskResponseDto(Ask entity){
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
    public AskResponseDto(Integer seq, Item item, AskTypeEnum type, User askUser, String askMsg, String askImg, User confirmUser, String confirmMsg, LocalDateTime createDate, String createUser, LocalDateTime modifyDate, String modifyUser) {
        this.seq = seq;
        this.item = item;
        this.type = type;
        this.askUser = askUser;
        this.askMsg = askMsg;
        this.askImg = askImg;
        this.confirmUser = confirmUser;
        this.confirmMsg = confirmMsg;
        this.createDate = createDate;
        this.createUser = createUser;
        this.modifyDate = modifyDate;
        this.modifyUser = modifyUser;
    }
}