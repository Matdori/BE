package com.example.beepoo.service;

import com.example.beepoo.dto.GlobalResponseDto;
import com.example.beepoo.dto.UserRequestDto;
import com.example.beepoo.dto.UserResponseDto;
import com.example.beepoo.entity.Department;
import com.example.beepoo.entity.User;
import com.example.beepoo.exception.CustomException;
import com.example.beepoo.exception.ErrorCode;
import com.example.beepoo.repository.DepartmentRepository;
import com.example.beepoo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    @Transactional
    public GlobalResponseDto<UserResponseDto> createUser(UserRequestDto userRequestDto) {

        //ToDo[07] : 권한확인 해야할까?

        //ToDo[07] : 유저를 생성하면서 부서를 생성하게 할 것인가??

        //이메일 중복 여부 확인
        if (userRepository.existsByUserEmail(userRequestDto.getUserEmail())) {
            throw new CustomException(ErrorCode.USER_ALREADY_EXIST);
        }

        //ToDo[07] : pw 암호화
        //부서 먼저 찾기
        Department department = departmentRepository.findDepartmentByDepartmentName(userRequestDto.getDepartmentName())
                .orElseThrow(()-> new CustomException(ErrorCode.DEPARTMENT_NOT_FOUND));

        User user = new User(userRequestDto);
        user.setDepartment(department);
        department.setUserCount(department.getUserCount() + 1);

        //ToDo[07] : user 생성자 확인하여 추가해줘야함 (user.setCreateUser();)
        userRepository.save(user);

        return GlobalResponseDto.ok("생성 완료", new UserResponseDto(user));

    }

    @Transactional
    public GlobalResponseDto<UserResponseDto> updateUser(UserRequestDto userRequestDto) {
        User user = userRepository.findUserByUserEmail(userRequestDto.getUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.updateUser(userRequestDto);
        return GlobalResponseDto.ok("수정 완료", new UserResponseDto(user));
    }

    @Transactional
    public GlobalResponseDto deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        userRepository.deleteById(userId);
        return GlobalResponseDto.ok("삭제 완료");
    }

    @Transactional
    public GlobalResponseDto<UserResponseDto> getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        userRepository.findById(userId);
        return GlobalResponseDto.ok("조회 성공", new UserResponseDto(user));
    }

    @Transactional
    public GlobalResponseDto<List<UserResponseDto>> getUserListByDepartment(String departmentName) {
        List<User> userList = userRepository.findUsersByDepartmentName(departmentName);
        List<UserResponseDto> dtoList = new ArrayList();
        for (User user: userList) {
            dtoList.add(new UserResponseDto(user));
        }
        return GlobalResponseDto.ok("조회 성공", dtoList);
    }

    @Transactional
    public GlobalResponseDto<Boolean> checkUserEmail(String email) {
        if(userRepository.existsByUserEmail(email)){
            return GlobalResponseDto.ok("중복된 이메일", false);
        }else{
            return GlobalResponseDto.ok("사용 가능한 이메일", true);
        }
    }
}
