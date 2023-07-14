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
}
