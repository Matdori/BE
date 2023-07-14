package com.example.beepoo.service;

import com.example.beepoo.dto.DepartmentRequestDto;
import com.example.beepoo.entity.Department;
import com.example.beepoo.exception.CustomException;
import com.example.beepoo.exception.ErrorCode;
import com.example.beepoo.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Transactional
    public int createDepartment (DepartmentRequestDto departmentRequestDto) {

        if (departmentRepository.existsByDepartmentName(departmentRequestDto.getDepartmentName())) {
            throw new CustomException(ErrorCode.AlreadyExistDepartment);
        }
        Department department = new Department(departmentRequestDto);

        //ToDo : 유저 등록 해야함
        department.setCREATE_USER("유저 이름 넣기");
        departmentRepository.save(department);
        return 1;
    }
}
