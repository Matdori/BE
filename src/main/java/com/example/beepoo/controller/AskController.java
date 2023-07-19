package com.example.beepoo.controller;

import com.example.beepoo.dto.AskRequestDto;
import com.example.beepoo.dto.AskResponseDto;
import com.example.beepoo.dto.GlobalResponseDto;
import com.example.beepoo.service.AskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ask")
public class AskController {

    private final AskService askService;

    // 요청 목록 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto<List<AskResponseDto>> getAskList(AskRequestDto condition, Pageable pageable) {
        return askService.getAskList(condition, pageable);
    }

    // 요청 상세 조회
    @GetMapping("/detail")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto<AskResponseDto> getAsk(@RequestParam("seq") Integer seq) {
        return askService.getAsk(seq);
    }

    // 요청 등록
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GlobalResponseDto insertAsk(@RequestBody AskRequestDto askDto) {
        return askService.insertAsk(askDto);
    }

    // 요청 수정
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto updateAsk(@RequestBody AskRequestDto askDto) {
        return askService.updateAsk(askDto);
    }

    // 요청 취소
    @PatchMapping("/cancel")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto cancelAsk(@RequestBody AskRequestDto askDto) {
        return askService.cancelAsk(askDto);
    }

    // 요청 확인(처리)
    @PatchMapping("/confirm")
    @ResponseStatus(HttpStatus.OK)
    public GlobalResponseDto confirmAsk(@RequestBody AskRequestDto askDto) {
        return askService.confirmAsk(askDto);
    }

    //요청 현황 건수 (대시보드) 필요 시 목록
    @GetMapping("/count")
    public GlobalResponseDto<Long> getAskCount(){
        return askService.getAskCount();
    }
}
