package com.example.beepoo.entity;

import com.example.beepoo.dto.ItemRequestDto;
import com.example.beepoo.enums.ItemStatusEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Item extends TimeStamp {

    @Id
    @GeneratedValue
    private Integer seq;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ItemStatusEnum status;

    @Column(nullable = true)
    private String serial;

    @Column(nullable = true)
    private String comment;

    @ManyToOne
    private ItemType typeCode;

    public Item(ItemRequestDto item, ItemType itemType) {
        this.name = item.getName();
        this.typeCode = itemType;
        this.status = item.getStatus();
        this.serial = item.getSerial();
        this.comment = item.getComment();
    }
}
