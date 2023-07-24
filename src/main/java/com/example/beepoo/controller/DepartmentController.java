package com.example.beepoo.controller;

import com.example.beepoo.dto.DepartmentRequestDto;
import com.example.beepoo.dto.DepartmentResponseDto;
import com.example.beepoo.dto.GlobalResponseDto;
import com.example.beepoo.service.DepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
@Tag(name="부서")
public class DepartmentController {
    private final DepartmentService departmentService;

    //부서 등록
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GlobalResponseDto<DepartmentResponseDto> createDepartment(@RequestBody DepartmentRequestDto departmentRequestDto,
                                                                     HttpServletRequest req) {
        return departmentService.createDepartment(departmentRequestDto, req);
    }

    //부서 수정
    @PutMapping("/{departmentId}")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto<DepartmentResponseDto> updateDepartment(@PathVariable Long departmentId,
                                                                     @RequestBody DepartmentRequestDto departmentRequestDto,
                                                                     HttpServletRequest req) {

        return departmentService.updateDepartment(departmentId, departmentRequestDto, req);
    }

    //부서 삭제
    @DeleteMapping("/{departmentId}")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto deleteDepartment(@PathVariable Long departmentId, HttpServletRequest req) {
        return departmentService.deleteDepartment(departmentId, req);
    }

    //부서 상세조회
    @GetMapping("/{departmentId}")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto<DepartmentResponseDto> getDepartment(@PathVariable Long departmentId,
                                                                  HttpServletRequest req) {
        return departmentService.getDepartment(departmentId, req);
    }

    //부서 전체 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto<List<DepartmentResponseDto>> getDepartmentList(HttpServletRequest req) {
        return departmentService.getDepartmentList(req);
    }

    // 부서명 중복 체크
    @GetMapping("/checkName/{name}")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto<Boolean> checkDepartmentName(@PathVariable String name) {
        return departmentService.checkDepartmentName(name);
    }
}
