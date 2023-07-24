package com.example.beepoo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class DepartmentRequestDto {
    @Schema(description = "부서 이름", example = "기획")
    private String departmentName;
}