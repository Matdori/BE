package com.example.beepoo.service;

import com.example.beepoo.dto.AskRequestDto;
import com.example.beepoo.dto.AskResponseDto;
import com.example.beepoo.dto.GlobalResponseDto;
import com.example.beepoo.entity.Ask;
import com.example.beepoo.entity.Item;
import com.example.beepoo.enums.AskTypeEnum;
import com.example.beepoo.enums.ItemStatusEnum;
import com.example.beepoo.enums.UserRoleEnum;
import com.example.beepoo.exception.CustomException;
import com.example.beepoo.exception.ErrorCode;
import com.example.beepoo.repository.AskCustomRepository;
import com.example.beepoo.repository.AskRepository;
import com.example.beepoo.repository.ItemRepository;
import com.example.beepoo.util.CurrentUser;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AskService {

    private final AskRepository askRepository;
    private final AskCustomRepository askCustomRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public GlobalResponseDto<List<AskResponseDto>> getAskList(
        HttpServletRequest req,
        AskRequestDto condition,
        Pageable pageable
    ) {
        // 사용자인 경우 해당 사용자의 요청만 조회
        if (!UserRoleEnum.ADMIN.equals(CurrentUser.getUserRole(req))) {
            condition.setAskUserName(CurrentUser.getUserName(req));
        }

        // 요청 목록 조회
        List<AskResponseDto> askList = askCustomRepository.getAskList(condition, pageable);

        return GlobalResponseDto.ok("요청 목록 조회 성공", askList);
    }

    @Transactional
    public GlobalResponseDto<AskResponseDto> getAsk(HttpServletRequest req, Integer seq) {
        // 요청 존재 여부 확인
        Ask askEntity = askRepository.findById(seq).orElseThrow(() -> new CustomException(ErrorCode.ASK_NOT_FOUND));
        // 사용자인 경우, 해당 사용자의 요청인지 확인
        if (!UserRoleEnum.ADMIN.equals(CurrentUser.getUserRole(req))) {
            if (askEntity.getAskUser().getId() != CurrentUser.getUserId(req)) {
                throw new CustomException(ErrorCode.ONLY_ASK_USER_VIEW);
            }
        }

        // 요청 상세 조회
        AskResponseDto ask = new AskResponseDto(askEntity);

        return GlobalResponseDto.ok("요청 상세 조회 성공", ask);
    }

    @Transactional
    public GlobalResponseDto<String> insertAsk(HttpServletRequest req, AskRequestDto askDto) {
        // 비품 존재 여부 확인
        Item item = itemRepository
            .findById(askDto.getItemSeq())
            .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
        // 비품 상태 확인
        if (ItemStatusEnum.ASSIGNED.equals(item.getStatus())) {
            throw new CustomException(ErrorCode.ITEM_ALREADY_ASSIGNED);
        } else if (ItemStatusEnum.DISCARD.equals(item.getStatus())) {
            throw new CustomException(ErrorCode.ITEM_ALREADY_DISCARD);
        }

        // 요청 등록
        Ask ask = new Ask(askDto, item);
        ask.setItem(item);
        ask.setType(AskTypeEnum.ASK);
        ask.setAskUser(CurrentUser.getUser(req));
        askRepository.save(ask);
        // 비품 상태 및 요청자 변경
        itemRepository.updateStatusAndaskUserBySeq(item.getSeq(), ItemStatusEnum.ASSIGNED, CurrentUser.getUser(req));

        return GlobalResponseDto.ok("요청 등록 성공");
    }

    @Transactional
    public GlobalResponseDto updateAsk(HttpServletRequest req, AskRequestDto askDto) {
        // 요청 존재 여부 확인
        Ask ask = askRepository
            .findById(askDto.getSeq())
            .orElseThrow(() -> new CustomException(ErrorCode.ASK_NOT_FOUND));
        // 요청 처리 여부 확인
        if (AskTypeEnum.CONFIRM.equals(ask.getType())) {
            throw new CustomException(ErrorCode.ASK_ALREADY_CONFIRM);
        }
        // 요청자 일치 여부 확인
        if (ask.getAskUser().getId() != CurrentUser.getUserId(req)) {
            throw new CustomException(ErrorCode.ONLY_ASK_USER_MODIFY);
        }

        // 요청 수정
        ask.updateAsk(askDto);

        return GlobalResponseDto.ok("요청 수정 성공");
    }

    @Transactional
    public GlobalResponseDto cancelAsk(HttpServletRequest req, AskRequestDto askDto) {
        // 요청 존재 여부 확인
        Ask ask = askRepository
            .findById(askDto.getSeq())
            .orElseThrow(() -> new CustomException(ErrorCode.ASK_NOT_FOUND));
        // 요청 처리 여부 확인
        if (AskTypeEnum.CONFIRM.equals(ask.getType())) {
            throw new CustomException(ErrorCode.ASK_ALREADY_CONFIRM);
        }
        // 요청자 일치 여부 확인
        if (ask.getAskUser().getId() != CurrentUser.getUserId(req)) {
            throw new CustomException(ErrorCode.ONLY_ASK_USER_CANCEL);
        }

        // 요청 취소
        ask.cancelAsk(askDto);
        // 비품 상태 및 요청자 변경
        itemRepository.updateStatusAndaskUserBySeq(ask.getItem().getSeq(), ItemStatusEnum.REGISTERED, null);

        return GlobalResponseDto.ok("요청 취소 성공");
    }

    @Transactional
    public GlobalResponseDto confirmAsk(HttpServletRequest req, AskRequestDto askDto) {
        // 요청 존재 여부 확인
        Ask ask = askRepository
            .findById(askDto.getSeq())
            .orElseThrow(() -> new CustomException(ErrorCode.ASK_NOT_FOUND));
        // 요청 처리 여부 확인
        if (AskTypeEnum.CONFIRM.equals(ask.getType())) {
            throw new CustomException(ErrorCode.ASK_ALREADY_CONFIRM);
        }
        // 처리자 세팅
        ask.setConfirmUser(CurrentUser.getUser(req));

        // 요청 처리
        ask.confirmAsk(askDto);

        return GlobalResponseDto.ok("요청 처리 성공");
    }

    @Transactional
    public GlobalResponseDto<Long> getAskCount() {
        return GlobalResponseDto.ok("요청 현황 건수", askRepository.countAskByType(AskTypeEnum.ASK));
    }

    @Transactional
    public GlobalResponseDto<Map<AskTypeEnum, String>> getAskType() {
        Map<AskTypeEnum, String> result = new HashMap<>();
        for (AskTypeEnum askType : AskTypeEnum.values()) {
            result.put(askType, askType.type());
        }

        return GlobalResponseDto.ok("요청 상태 조회 성공", result);
    }
}
