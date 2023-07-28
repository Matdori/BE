package com.example.beepoo.enums;

public enum AskTypeEnum {
    ASK("요청"),
    CONFIRM("처리 완료"),
    CANCEL("요청 취소");

    private final String type;

    AskTypeEnum(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
}
