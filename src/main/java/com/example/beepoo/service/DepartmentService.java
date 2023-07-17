package com.example.beepoo.service;

import com.example.beepoo.dto.DepartmentRequestDto;
import com.example.beepoo.dto.DepartmentResponseDto;
import com.example.beepoo.dto.GlobalResponseDto;
import com.example.beepoo.entity.Department;
import com.example.beepoo.exception.CustomException;
import com.example.beepoo.exception.ErrorCode;
import com.example.beepoo.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Transactional
    public int createDepartment (DepartmentRequestDto departmentRequestDto) {

        if (departmentRepository.existsByDepartmentName(departmentRequestDto.getDepartmentName())) {
            throw new CustomException(ErrorCode.DEPARTMENT_ALREADY_EXIST);
        }
        Department department = new Department(departmentRequestDto);

        //ToDo[07] : 유저 등록 해야함
        department.setCreateUser("유저 이름 넣기");
        departmentRepository.save(department);
        return 1;
    }

    @Transactional
    public GlobalResponseDto<DepartmentResponseDto> updateDepartment(Long departmentId, DepartmentRequestDto departmentRequestDto) {

        Department department = departmentRepository.findById(departmentId).orElseThrow(()-> new IllegalArgumentException());
        department.update(departmentRequestDto);

        return GlobalResponseDto.ok("업데이트했슈", new DepartmentResponseDto(department));
    }

    @Transactional
    public GlobalResponseDto deleteDepartment(Long departmentId) {
        //ToDo[07] : 존재하는 아이디인지 먼저 확인 해야할까
        departmentRepository.deleteById(departmentId);
        return GlobalResponseDto.ok("삭제했슈");
    }

    @Transactional
    public GlobalResponseDto<DepartmentResponseDto> getDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(()-> new IllegalArgumentException());
        return GlobalResponseDto.ok("단건조회", new DepartmentResponseDto(department));
    }
    
    @Transactional
    public GlobalResponseDto<List<DepartmentResponseDto>> getDepartmentList() {
        List<Department> departmentList = departmentRepository.findAll();
        List<DepartmentResponseDto> departmentResponseDtoList = new ArrayList<>();
        for (Department department : departmentList) {
            departmentResponseDtoList.add(new DepartmentResponseDto(department));
        }
        return GlobalResponseDto.ok("리스트 조회", departmentResponseDtoList);
    }
}
