package com.example.beepoo.service;

import com.example.beepoo.dto.*;
import com.example.beepoo.entity.Department;
import com.example.beepoo.entity.User;
import com.example.beepoo.exception.CustomException;
import com.example.beepoo.exception.ErrorCode;
import com.example.beepoo.jwt.JwtUtil;
import com.example.beepoo.repository.DepartmentRepository;
import com.example.beepoo.repository.UserRepository;
import com.example.beepoo.util.CurrentUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    //로그인
    @Transactional
    public GlobalResponseDto<UserResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse res) {
        //존재 여부 확인
        User user = userRepository
            .findUserByUserEmail(loginRequestDto.getUserEmail())
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        //비밀번호 확인
        if (!passwordEncoder.matches(loginRequestDto.getUserPassword(), user.getUserPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        String token = jwtUtil.createToken(user.getId(), user.getUserRole());
        jwtUtil.addJwtToCookie(token, res);

        //다른방식
        jwtUtil.issueToken(res, token);

        return GlobalResponseDto.ok("로그인 성공", new UserResponseDto(user));
    }

    //유저등록
    @Transactional
    public GlobalResponseDto<UserResponseDto> createUser(UserRequestDto userRequestDto, HttpServletRequest req) {
        User currentUser = CurrentUser.getUser(req);
        CurrentUser.checkAuthorization(currentUser);

        //이메일 중복 여부 확인
        if (userRepository.existsByUserEmail(userRequestDto.getUserEmail())) {
            throw new CustomException(ErrorCode.USER_ALREADY_EXIST);
        }

        //ToDo[07] : 유저를 생성하면서 부서를 생성하게 할 것인가??
        //부서 존재 여부 확인
        Department department = departmentRepository
            .findDepartmentByDepartmentName(userRequestDto.getDepartmentName())
            .orElseThrow(() -> new CustomException(ErrorCode.DEPARTMENT_NOT_FOUND));

        String encodedPassword = passwordEncoder.encode(userRequestDto.getUserPassword());

        User user = new User(userRequestDto, encodedPassword);
        user.setDepartment(department);
        department.setUserCount(department.getUserCount() + 1);
        user.setCreateUser(currentUser.getId().toString());

        userRepository.save(user);

        return GlobalResponseDto.ok("생성 완료", new UserResponseDto(user));
    }

    //유저 수정
    @Transactional
    public GlobalResponseDto<UserResponseDto> updateUser(UserRequestDto userRequestDto, HttpServletRequest req) {
        //ToDo[07] : email도 수정할 수 있도록 할 것인가?, 본인 외 수정 권한 없는 상태
        User currentUser = CurrentUser.getUser(req);
        User user = userRepository
            .findUserByUserEmail(userRequestDto.getUserEmail())
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        //본인이 아니면 수정할 수 없도록
        if (!Objects.equals(currentUser.getId(), user.getId())) {
            throw new CustomException(ErrorCode.USER_UNAUTHORIZED);
        }

        user.updateUser(userRequestDto);
        return GlobalResponseDto.ok("수정 완료", new UserResponseDto(user));
    }

    //유저 삭제
    @Transactional
    public GlobalResponseDto deleteUser(Long userId, HttpServletRequest req) {
        //권한 확인
        User currentUser = CurrentUser.getUser(req);
        CurrentUser.checkAuthorization(currentUser);

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        userRepository.deleteById(userId);
        return GlobalResponseDto.ok("삭제 완료");
    }

    //유저 조회
    @Transactional
    public GlobalResponseDto<UserResponseDto> getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        userRepository.findById(userId);
        return GlobalResponseDto.ok("조회 성공", new UserResponseDto(user));
    }

    //유저목록 조회(부서)
    @Transactional
    public GlobalResponseDto<List<UserResponseDto>> getUserListByDepartment(
        String departmentName,
        HttpServletRequest req
    ) {
        //권한 확인
        User currentUser = CurrentUser.getUser(req);
        CurrentUser.checkAuthorization(currentUser);

        List<User> userList = userRepository.findUsersByDepartmentName(departmentName);
        List<UserResponseDto> dtoList = new ArrayList();
        for (User user : userList) {
            dtoList.add(new UserResponseDto(user));
        }
        return GlobalResponseDto.ok("조회 성공", dtoList);
    }

    @Transactional
    public GlobalResponseDto<Boolean> checkUserEmail(String email) {
        if (userRepository.existsByUserEmail(email)) {
            return GlobalResponseDto.ok("중복된 이메일", false);
        } else {
            return GlobalResponseDto.ok("사용 가능한 이메일", true);
        }
    }

    //비밀번호 변경
    @Transactional
    public GlobalResponseDto updatePassword(PasswordRequestDto passwordRequestDto, HttpServletRequest req) {
        User currentUser = CurrentUser.getUser(req);
        User user = userRepository
            .findById(passwordRequestDto.getUserId())
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        //ToDo[07] : 확인 해야하나?
        if (!Objects.equals(user.getId(), currentUser.getId())) {
            throw new CustomException(ErrorCode.USER_NOT_MATCH);
        }

        //비밀번호 일치 확인
        if (!passwordEncoder.matches(passwordRequestDto.getOldPassword(), user.getUserPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        user.setUserPassword(passwordEncoder.encode(passwordRequestDto.getNewPassword()));
        //ToDo[07] : 토큰 만료 못시키는데 새로 발급해줘야하나?
        return GlobalResponseDto.ok("비밀번호 변경 완료");
    }

    //내 정보 조회
    public GlobalResponseDto<UserResponseDto> getMyInfo(HttpServletRequest req) {
        User currentUser = CurrentUser.getUser(req);
        return GlobalResponseDto.ok("조회 성공", new UserResponseDto(currentUser));
    }
}
