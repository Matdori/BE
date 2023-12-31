package com.example.beepoo.controller;

import com.example.beepoo.dto.TestRequestDto;
import com.example.beepoo.dto.TestResponseDto;
import com.example.beepoo.entity.User;
import com.example.beepoo.service.TestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TestController {

    private final TestService testService;

    @PostMapping("/test")
    public ResponseEntity<TestResponseDto> test(@RequestBody TestRequestDto requestDto, HttpServletRequest req) {
        User user = (User)req.getAttribute("user");
        System.out.println(user.getUserName());
        return testService.testService(requestDto);
    }

    @GetMapping("/jenkins")
    public String jenkinsTest() {

        return "Jenkins Test Success !";
    }
}
