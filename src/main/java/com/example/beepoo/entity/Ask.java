package com.example.beepoo.entity;

import com.example.beepoo.dto.AskRequestDto;
import com.example.beepoo.enums.AskTypeEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Ask extends TimeStamp {

    @Id
    @GeneratedValue()
    private Integer seq;

    @JoinColumn
    @ManyToOne
    private Item item;

    @Column(nullable = false)
    private AskTypeEnum type;

    @JoinColumn
    @ManyToOne
    private User askUser;

    @Column(nullable = true)
    private String askMsg;

    @Column(nullable = true)
    private String askImg;

    @JoinColumn
    @ManyToOne
    private User confirmUser;

    @Column(nullable = true)
    private String confirmMsg;

    public Ask(AskRequestDto ask, Item item, User user) {
        this.item = item;
        this.type = ask.getAskType();
        this.askUser = user;
        this.askMsg = ask.getAskMsg();
        this.askImg = ask.getAskImg();
    }

    public void updateAsk(AskRequestDto ask){
        this.seq = ask.getSeq();
        this.askMsg = ask.getAskMsg();
        this.askImg = ask.getAskImg();
    }

    public void cancelAsk(AskRequestDto ask){
        this.seq = ask.getSeq();
        this.type = AskTypeEnum.CANCEL;
    }

    public void confirmAsk(AskRequestDto ask){
        this.seq = ask.getSeq();
        this.type = AskTypeEnum.CONFIRM;
        // this.confirmUser = user;
        this.confirmMsg = ask.getConfirmMsg();
    }
}
