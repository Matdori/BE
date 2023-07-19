package com.example.beepoo.controller;

import com.example.beepoo.dto.GlobalResponseDto;
import com.example.beepoo.dto.UserRequestDto;
import com.example.beepoo.dto.UserResponseDto;
import com.example.beepoo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //사원 등록
    @PostMapping
    public GlobalResponseDto<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    //사원 수정
    @PutMapping
    public GlobalResponseDto<UserResponseDto> updateUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.updateUser(userRequestDto);
    }

    //사원 삭제
    @DeleteMapping("/{userId}")
    public GlobalResponseDto deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

    //사원 조회
    @GetMapping("/{userId}")
    public GlobalResponseDto<UserResponseDto> getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    //부서 내 사원 조회
    @GetMapping("/department")
    public GlobalResponseDto<List<UserResponseDto>> getUserListByDepartment(@RequestParam("name") String departmentName) {
        return userService.getUserListByDepartment(departmentName);
    }

    //비밀번호 변경
    //Todo[07] : jwt 이후 비밀번호 변경 구현


    //내 정보 조회
    //ToDo[07] : 인증 방식에 따라 달라질 예정
}
