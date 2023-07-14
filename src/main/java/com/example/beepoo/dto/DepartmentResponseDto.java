package com.example.beepoo.dto;

import com.example.beepoo.entity.Department;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DepartmentResponseDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String departmentName;

    public DepartmentResponseDto(Department department){
        this.departmentName = department.getDepartmentName();
    }
}