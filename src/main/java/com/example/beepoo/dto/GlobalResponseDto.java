package com.example.beepoo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GlobalResponseDto<T> {

    private String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private long totalCnt;

    public GlobalResponseDto(String msg) {
        this.msg = msg;
    }

    public GlobalResponseDto(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }

    public static <T> GlobalResponseDto<T> ok(String msg, T data) {
        return new GlobalResponseDto<>(msg, data);
    }

    public static GlobalResponseDto ok(String msg) {
        return new GlobalResponseDto(msg);
    }

    public static <T> GlobalResponseDto<T> ok(String msg, T data, long totalCnt) {
        return new GlobalResponseDto<>(msg, data, totalCnt);
    }
}
