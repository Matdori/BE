package com.example.beepoo.entity;

import com.example.beepoo.dto.ItemTypeRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class ItemType extends TimeStamp{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //상위 코드
//    @Column(nullable = true)
//    private Long parentCode;

    @ManyToOne
    @JoinColumn(name = "parentTypeCode")
    private ItemType parentCode;

    @Column(nullable = false)
    private String type;

    @Column
    private Integer depth;

    @OneToMany
    private List<Item> itemList = new ArrayList<>();

    public ItemType(ItemTypeRequestDto itemTypeRequestDto, ItemType parentType, Integer depth) {
        this.parentCode = parentType;
        this.type = itemTypeRequestDto.getType();
        this.depth = depth;
    }
}
