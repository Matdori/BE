package com.example.beepoo.enums;

public enum ItemStatusEnum {
    REGISTERED("registered"),
    ASSIGNED("assigned"),
    DISCARD("discard");

    private final String status;

    ItemStatusEnum(String status) {
        this.status = status;
    }

    public String status() {
        return status;
    }
}
