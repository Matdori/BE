package com.example.beepoo.controller;

import com.example.beepoo.dto.*;
import com.example.beepoo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
@Tag(name="유저")
public class UserController {

    private final UserService userService;

    //로그인
    @Operation(summary = "로그인", description = "로그인 api")
    @PostMapping("/login")
    public GlobalResponseDto<UserResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse res) {
        return userService.login(loginRequestDto, res);
    }

    //사원등록
    @Operation(summary = "사원등록", description = "관리자 권한 필요")
    @PostMapping
    public GlobalResponseDto<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto userRequestDto, HttpServletRequest req) {
        return userService.createUser(userRequestDto, req);
    }

    //사원 수정
    @Operation(summary = "사원 수정", description = "본인만 수정 가능")
    @PatchMapping
    public GlobalResponseDto<UserResponseDto> updateUser(@RequestBody UserRequestDto userRequestDto, HttpServletRequest req) {
        return userService.updateUser(userRequestDto, req);
    }

    //사원 삭제
    @Operation(summary = "사원 삭제", description = "관리자 권한 필요")
    @DeleteMapping("/{userId}")
    public GlobalResponseDto deleteUser(@PathVariable Long userId, HttpServletRequest req) {
        return userService.deleteUser(userId, req);
    }

    //사원 조회
    @Operation(summary = "사원 조회", description = "사원 정보 조회 api")
    @GetMapping("/{userId}")
    public GlobalResponseDto<UserResponseDto> getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    //부서 내 사원 조회
    @Operation(summary = "부서 내 사원 목록 조회", description = "관리자 권한 필요")
    @GetMapping("/department")
    public GlobalResponseDto<List<UserResponseDto>> getUserListByDepartment(@RequestParam("name") String departmentName, HttpServletRequest req) {
        return userService.getUserListByDepartment(departmentName, req);
    }


    //비밀번호 변경
    @Operation(summary = "비밀번호 변경", description = "기존 비밀번호 일치 시")
    @PatchMapping("/password")
    public GlobalResponseDto updatePassword(@RequestBody PasswordRequestDto passwordRequestDto,
                                            HttpServletRequest req){
        return userService.updatePassword(passwordRequestDto, req);
    }

    //내 정보 조회
    @Operation(summary = "내 정보 조회", description = "기존 비밀번호 일치 시")
    @GetMapping("/info")
    public GlobalResponseDto<UserResponseDto> getMyInfo(HttpServletRequest req){
        return userService.getMyInfo(req);
    }

    // 이메일 중복 체크
    @Operation(summary = "email 중복 체크", description = "email 중복 체크 api")
    @GetMapping("/checkEmail/{email}")
    public GlobalResponseDto<Boolean> checkUserEmail(@PathVariable String email) {
        return userService.checkUserEmail(email);
    }


}