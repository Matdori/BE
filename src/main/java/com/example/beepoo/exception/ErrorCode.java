package com.example.beepoo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    //ToDo[07] : 코드 중복제거, 카테고리별 정렬
    // 부서 Error Code
    DEPARTMENT_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR.value(), "D001", "해당 부서를 찾을 수 없습니다."),
    DEPARTMENT_ALREADY_EXIST(HttpStatus.CONFLICT.value(), "D002", "이미 등록된 부서입니다."),

    // 사용자 Error Code
    USER_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR.value(), "U001", "존재하지 않는 사용자입니다."),
    USER_ALREADY_EXIST(HttpStatus.CONFLICT.value(), "U002", "이미 등록된 사용자입니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST.value(), "U003", "비밀번호가 일치하지 않습니다."),

    // 비품 Error Code
    ITEM_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR.value(), "I001", "해당 비품을 찾을 수 없습니다."),
    ITEM_ALREADY_EXIST(HttpStatus.CONFLICT.value(), "I002", "이미 존재하는 비품입니다."),
    ITEM_ALREADY_ASSIGNED(HttpStatus.CONFLICT.value(), "I003", "이미 할당된 비품입니다."),
    ITEM_ALREADY_DISCARD(HttpStatus.CONFLICT.value(), "I004", "이미 폐기된 비품입니다."),

    // 요청 Error Code
    ASK_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR.value(), "A001", "존재하지 않는 요청입니다."),
    ASK_ALREADY_CONFIRM(HttpStatus.INTERNAL_SERVER_ERROR.value(), "A002", "이미 처리된 요청입니다."),
    ONLY_ASK_USER_VIEW(HttpStatus.INTERNAL_SERVER_ERROR.value(), "A003", "요청자만 조회할 수 있습니다."),
    ONLY_ASK_USER_MODIFY(HttpStatus.INTERNAL_SERVER_ERROR.value(), "A003", "요청자만 수정할 수 있습니다."),
    ONLY_ASK_USER_CANCEL(HttpStatus.INTERNAL_SERVER_ERROR.value(), "A003", "요청자만 취소할 수 있습니다.");

    private final int httpStatus;
    private final String errorCode;
    private final String message;
}
