package com.example.beepoo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class User extends TimeStamp{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long USER_ID;

    @Column
    private String USER_NAME;

    @Column
    private String USER_EMAIL;

    @Column
    private String PASSWORD;

    @Column
    private String AUTHORITY;

    @JoinColumn
    @ManyToOne
    private Department department;

}
