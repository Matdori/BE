package com.example.beepoo.dto;

import com.example.beepoo.entity.Department;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponseDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String departmentName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer count;

    public DepartmentResponseDto(Department department){
        this.departmentName = department.getDepartmentName();
    }

    public DepartmentResponseDto(Department department, Integer count) {
        this.departmentName = department.getDepartmentName();
        this.count = count;
    }
}