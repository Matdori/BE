package com.example.beepoo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    NotFoundItem(HttpStatus.NOT_FOUND.value(), "I001", "해당 비품을 찾을 수 없습니다.");

    private final int httpStatus;
    private final String errorCode;
    private final String message;
}