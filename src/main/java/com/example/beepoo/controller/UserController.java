package com.example.beepoo.controller;

import com.example.beepoo.dto.*;
import com.example.beepoo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //로그인
    @GetMapping("/login")
    public GlobalResponseDto<UserResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse res) {
        return userService.login(loginRequestDto, res);
    }

    //사원등록
    @PostMapping
    public GlobalResponseDto<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto userRequestDto, HttpServletRequest req) {
        return userService.createUser(userRequestDto, req);
    }

    //사원 수정
    @PutMapping
    public GlobalResponseDto<UserResponseDto> updateUser(@RequestBody UserRequestDto userRequestDto, HttpServletRequest req) {
        return userService.updateUser(userRequestDto, req);
    }

    //사원 삭제
    @DeleteMapping("/{userId}")
    public GlobalResponseDto deleteUser(@PathVariable Long userId, HttpServletRequest req) {
        return userService.deleteUser(userId, req);
    }

    //사원 조회
    @GetMapping("/{userId}")
    public GlobalResponseDto<UserResponseDto> getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    //부서 내 사원 조회
    @GetMapping("/department")
    public GlobalResponseDto<List<UserResponseDto>> getUserListByDepartment(@RequestParam("name") String departmentName, HttpServletRequest req) {
        return userService.getUserListByDepartment(departmentName, req);
    }


    //비밀번호 변경
    @PatchMapping("/password")
    public GlobalResponseDto updatePassword(@RequestBody PasswordRequestDto passwordRequestDto,
                                            HttpServletRequest req){
        return userService.updatePassword(passwordRequestDto, req);
    }

    //내 정보 조회
    //ToDo[07] : 인증 방식에 따라 달라질 예정
    @GetMapping("/info")
    public GlobalResponseDto<UserResponseDto> getMyInfo(HttpServletRequest req){
        return userService.getMyInfo(req);
    }

    // 이메일 중복 체크
    @GetMapping("/checkEmail/{email}")
    public GlobalResponseDto<Boolean> checkUserEmail(@PathVariable String email) {
        return userService.checkUserEmail(email);
    }


}