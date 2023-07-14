package com.example.beepoo.controller;

import com.example.beepoo.dto.DepartmentRequestDto;
import com.example.beepoo.dto.DepartmentResponseDto;
import com.example.beepoo.dto.GlobalResponseDto;
import com.example.beepoo.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    //부서 등록
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GlobalResponseDto<?> createDepartment(@RequestBody DepartmentRequestDto departmentRequestDto) {
        int i = departmentService.createDepartment(departmentRequestDto);
        return GlobalResponseDto.builder().msg("성공했슈" + i).data(new DepartmentResponseDto()).build();
    }

    //부서 수정
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto<DepartmentResponseDto> updateDepartment(@PathVariable Long departmentId,
                                                                     @RequestBody DepartmentRequestDto departmentRequestDto) {

        return departmentService.updateDepartment(departmentId, departmentRequestDto);
    }

    //부서 삭제
    @DeleteMapping("/{departmentId}")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto deleteDepartment(@PathVariable Long departmentId) {
        return departmentService.deleteDepartment(departmentId);
    }

    //부서 상세조회
    @GetMapping("/{departmentId}")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto<DepartmentResponseDto> getDepartment(@PathVariable Long departmentId) {
        return departmentService.getDepartment(departmentId);
    }

    //부서 전체 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto<List<DepartmentResponseDto>> getDepartmentList() {
        return departmentService.getDepartmentList();
    }

}
