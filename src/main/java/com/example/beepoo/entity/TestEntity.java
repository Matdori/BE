package com.example.beepoo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter
@NoArgsConstructor
public class TestEntity {

    @Id
    @GeneratedValue()
    private Long id;

    @Column(nullable = false)
    private String word;
}
