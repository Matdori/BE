package com.example.beepoo.controller;

import com.example.beepoo.dto.AskRequestDto;
import com.example.beepoo.dto.AskResponseDto;
import com.example.beepoo.dto.GlobalResponseDto;
import com.example.beepoo.dto.MapResponseDto;
import com.example.beepoo.enums.AskTypeEnum;
import com.example.beepoo.service.AskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ask")
@Tag(name = "요청")
public class AskController {

    private final AskService askService;

    // 요청 목록 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto<List<AskResponseDto>> getAskList(
        HttpServletRequest req,
        AskRequestDto condition,
        Pageable pageable
    ) {
        return askService.getAskList(req, condition, pageable);
    }

    // 요청 상세 조회
    @GetMapping("/detail")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto<AskResponseDto> getAsk(HttpServletRequest req, @RequestParam("seq") Integer seq) {
        return askService.getAsk(req, seq);
    }

    // 요청 등록
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GlobalResponseDto insertAsk(HttpServletRequest req, @RequestBody AskRequestDto askDto) {
        return askService.insertAsk(req, askDto);
    }

    // 요청 수정
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto updateAsk(HttpServletRequest req, @RequestBody AskRequestDto askDto) {
        return askService.updateAsk(req, askDto);
    }

    // 요청 취소
    @PatchMapping("/cancel")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto cancelAsk(HttpServletRequest req, @RequestBody AskRequestDto askDto) {
        return askService.cancelAsk(req, askDto);
    }

    // 요청 확인(처리)
    @PatchMapping("/confirm")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto confirmAsk(HttpServletRequest req, @RequestBody AskRequestDto askDto) {
        return askService.confirmAsk(req, askDto);
    }

    //요청 현황 건수 (대시보드) 필요 시 목록
    @GetMapping("/count")
    public GlobalResponseDto<Long> getAskCount() {
        return askService.getAskCount();
    }

    // 요청 상태 조회
    @GetMapping("/type")
    public GlobalResponseDto<List<MapResponseDto<AskTypeEnum, String>>> getAskType() {
        return askService.getAskType();
    }
}
