package com.example.beepoo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Builder
public class GlobalResponseDto<T> {

    private String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public GlobalResponseDto(String msg){
        this.msg = msg;
    }

    public static <T> GlobalResponseDto <T> ok(String msg, T data){
        return new GlobalResponseDto<>(msg, data);
    }

    public static GlobalResponseDto ok(String msg){
        return new GlobalResponseDto(msg);
    }
}
