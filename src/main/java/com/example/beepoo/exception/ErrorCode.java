package com.example.beepoo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // 400 BAD_REQUEST : 잘못된 요청
    // 401 UNAUTHORIZED : 권한 없음
    // 404 NOT_FOUND : 존재하지 않는 자원
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "I001", "해당 비품을 찾을 수 없습니다."),
    // 409 CONFLICT : 자원 충돌, 중복 데이터
    ITEM_EXIST(HttpStatus.CONFLICT.value(), "I002", "이미 존재하는 비품입니다.");

    private final int httpStatus;
    private final String errorCode;
    private final String message;
}