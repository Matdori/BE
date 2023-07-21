package com.example.beepoo.service;

import com.example.beepoo.dto.DepartmentRequestDto;
import com.example.beepoo.dto.DepartmentResponseDto;
import com.example.beepoo.dto.GlobalResponseDto;
import com.example.beepoo.entity.Department;
import com.example.beepoo.entity.User;
import com.example.beepoo.exception.CustomException;
import com.example.beepoo.exception.ErrorCode;
import com.example.beepoo.repository.DepartmentRepository;
import com.example.beepoo.util.CustomUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    //부서 등록
    @Transactional
    public GlobalResponseDto<DepartmentResponseDto> createDepartment(DepartmentRequestDto departmentRequestDto,
                                                                     HttpServletRequest req) {
        User currentUser = CustomUtil.getUserFromReq(req);
        CustomUtil.checkAuthorization(currentUser);

        if (departmentRepository.existsByDepartmentName(departmentRequestDto.getDepartmentName())) {
            throw new CustomException(ErrorCode.DEPARTMENT_ALREADY_EXIST);
        }
        Department department = new Department(departmentRequestDto);

        department.setCreateUser(currentUser.getId().toString());

        departmentRepository.save(department);
        return GlobalResponseDto.ok("부서 등록 성공", new DepartmentResponseDto(department));
    }

    //부서 수정
    @Transactional
    public GlobalResponseDto<DepartmentResponseDto> updateDepartment(Long departmentId,
                                                                     DepartmentRequestDto departmentRequestDto,
                                                                     HttpServletRequest req) {
        User currentUser = CustomUtil.getUserFromReq(req);
        CustomUtil.checkAuthorization(currentUser);

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(()-> new CustomException(ErrorCode.DEPARTMENT_NOT_FOUND));
        department.update(departmentRequestDto);

        return GlobalResponseDto.ok("업데이트 성공", new DepartmentResponseDto(department));
    }

    @Transactional
    public GlobalResponseDto deleteDepartment(Long departmentId, HttpServletRequest req) {
        //ToDo[07] : 존재하는 아이디인지 먼저 확인 해야할까
        User currentUser = CustomUtil.getUserFromReq(req);
        CustomUtil.checkAuthorization(currentUser);

        if (!departmentRepository.existsById(departmentId)){
            throw new CustomException(ErrorCode.DEPARTMENT_NOT_FOUND);
        }

        departmentRepository.deleteById(departmentId);
        return GlobalResponseDto.ok("삭제 성공");
    }

    @Transactional
    public GlobalResponseDto<DepartmentResponseDto> getDepartment(Long departmentId, HttpServletRequest req) {
        User currentUser = CustomUtil.getUserFromReq(req);
        CustomUtil.checkAuthorization(currentUser);

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(()-> new CustomException(ErrorCode.DEPARTMENT_NOT_FOUND));

        return GlobalResponseDto.ok("단건조회", new DepartmentResponseDto(department));
    }
    
    @Transactional
    public GlobalResponseDto<List<DepartmentResponseDto>> getDepartmentList(HttpServletRequest req) {
        User currentUser = CustomUtil.getUserFromReq(req);
        CustomUtil.checkAuthorization(currentUser);

        List<Department> departmentList = departmentRepository.findAll();
        List<DepartmentResponseDto> departmentResponseDtoList = new ArrayList<>();
        for (Department department : departmentList) {
            departmentResponseDtoList.add(new DepartmentResponseDto(department, department.getUserCount()));
        }
        return GlobalResponseDto.ok("리스트 조회", departmentResponseDtoList);
    }

    @Transactional
    public GlobalResponseDto<Boolean> checkDepartmentName(String name) {
        if(departmentRepository.existsByDepartmentName(name)){
            return GlobalResponseDto.ok("중복된 부서명", false);
        }else{
            return GlobalResponseDto.ok("사용 가능한 부서명", true);
        }
    }
}
