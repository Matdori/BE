package com.example.beepoo.entity;

import com.example.beepoo.dto.ItemRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Item extends TimeStamp {

    @Id
    @GeneratedValue()
    private Integer seq;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer typeCode;

    @Column(nullable = false)
    private Integer statusCode;

    @Column(nullable = true)
    private String serial;

    @Column(nullable = true)
    private String comment;

    public Item(ItemRequestDto item) {
        this.name = item.getName();
        this.typeCode = item.getTypeCode();
        this.statusCode = item.getStatusCode();
        this.serial = item.getSerial();
        this.comment = item.getComment();
    }
}
