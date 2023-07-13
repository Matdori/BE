package com.example.beepoo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    AlreadyExistDepartment(HttpStatus.BAD_REQUEST.value(), "R001", "이미 등록된 부서 입니다.");

    private final int httpStatus;
    private final String errorCode;
    private final String message;

}
