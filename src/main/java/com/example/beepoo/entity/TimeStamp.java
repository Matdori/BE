package com.example.beepoo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
public abstract class TimeStamp {

    @CreatedDate
    private LocalDateTime CREATE_DATE;

    @LastModifiedDate
    private LocalDateTime MODIFY_DATE;

    private String CREATE_USER;

    private String MODIFY_USER;
}
