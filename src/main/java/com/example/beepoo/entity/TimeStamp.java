package com.example.beepoo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class TimeStamp {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    //Todo[07]
    @CreatedBy
    @Column(updatable = false)
    private String createUser;

    @LastModifiedDate
    @Column(updatable = true)
    private LocalDateTime modifyDate;

    @LastModifiedBy
    @Column(updatable = true)
    private String modifyUser;

    @ManyToOne
    @JoinColumn(updatable = false, name = "created")
    private User created;
}
