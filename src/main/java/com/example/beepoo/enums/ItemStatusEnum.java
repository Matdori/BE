package com.example.beepoo.enums;

public enum ItemStatusEnum {
    REGISTERED("등록"),
    ASSIGNED("사용 중"),
    REPAIR("수리 중"),
    DISCARD("폐기");

    private final String status;

    ItemStatusEnum(String status) {
        this.status = status;
    }

    public String status() {
        return status;
    }
}
