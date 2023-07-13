package com.example.beepoo.controller;

import com.example.beepoo.dto.DepartmentRequestDto;
import com.example.beepoo.dto.DepartmentResponseDto;
import com.example.beepoo.dto.GlobalResponseDto;
import com.example.beepoo.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GlobalResponseDto<?> createDepartment (@RequestBody DepartmentRequestDto departmentRequestDto) {
        int i = departmentService.createDepartment(departmentRequestDto);
        return GlobalResponseDto.builder().msg("성공했슈" + i).data(new DepartmentResponseDto()).build();
    }
}
