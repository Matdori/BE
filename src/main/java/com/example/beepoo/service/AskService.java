package com.example.beepoo.service;

import com.example.beepoo.dto.AskRequestDto;
import com.example.beepoo.dto.AskResponseDto;
import com.example.beepoo.dto.GlobalResponseDto;
import com.example.beepoo.entity.Ask;
import com.example.beepoo.entity.Item;
import com.example.beepoo.entity.User;
import com.example.beepoo.enums.AskTypeEnum;
import com.example.beepoo.exception.CustomException;
import com.example.beepoo.exception.ErrorCode;
import com.example.beepoo.repository.AskCustomRepository;
import com.example.beepoo.repository.AskRepository;
import com.example.beepoo.repository.ItemRepository;
import com.example.beepoo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AskService {

    private final AskRepository askRepository;
    private final AskCustomRepository askCustomRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
    public GlobalResponseDto<List<AskResponseDto>> getAskList(AskRequestDto condition, Pageable pageable) {
        List<AskResponseDto> askList = askCustomRepository.getAskList(condition, pageable);

        return GlobalResponseDto.ok("요청 목록 조회 성공", askList);
    }

    @Transactional
    public GlobalResponseDto<AskResponseDto> getAsk(Integer seq) {
        Ask askEntity = askRepository.findById(seq).orElseThrow(
                () -> new CustomException(ErrorCode.ASK_NOT_FOUND));
        AskResponseDto ask = new AskResponseDto(askEntity);

        return GlobalResponseDto.ok("요청 상세 조회 성공", ask);
    }

    @Transactional
    public GlobalResponseDto<String> insertAsk(AskRequestDto askDto) {
        // 비품 존재 여부 확인
        Item item = itemRepository.findById(askDto.getItemSeq()).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
        // 사용자 존재 여부 확인
        User user = userRepository.findById(askDto.getAskUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        // TODO(337): 비품 상태 확인 필요(폐기된 비품인 경우 등)

        Ask ask = new Ask(askDto, item, user);
        ask.setItem(item);
        ask.setType(AskTypeEnum.ASK);
        ask.setAskUser(user);
        askRepository.save(ask);

        return GlobalResponseDto.ok("요청 등록 성공");
    }

    @Transactional
    public GlobalResponseDto updateAsk(AskRequestDto askDto) {
        // 요청 존재 여부 확인
        Ask ask = askRepository.findById(askDto.getSeq())
                .orElseThrow(() -> new CustomException(ErrorCode.ASK_NOT_FOUND));
        // 요청 처리 여부 확인
        if(AskTypeEnum.CONFIRM.equals(ask.getType())){
            throw new CustomException(ErrorCode.ASK_ALREADY_CONFIRM);
        }
        // TODO(337): 요청자와 일치하는지 확인

        ask.updateAsk(askDto);

        return GlobalResponseDto.ok("요청 수정 성공");
    }

    @Transactional
    public GlobalResponseDto cancelAsk(AskRequestDto askDto) {
        // 요청 존재 여부 확인
        Ask ask = askRepository.findById(askDto.getSeq())
                .orElseThrow(() -> new CustomException(ErrorCode.ASK_NOT_FOUND));
        // 요청 처리 여부 확인
        if(AskTypeEnum.CONFIRM.equals(ask.getType())){
            throw new CustomException(ErrorCode.ASK_ALREADY_CONFIRM);
        }
        // TODO(337): 요청자와 일치하는지 확인

        ask.cancelAsk(askDto);

        return GlobalResponseDto.ok("요청 취소 성공");
    }

    @Transactional
    public GlobalResponseDto confirmAsk(AskRequestDto askDto) {
        // 요청 존재 여부 확인
        Ask ask = askRepository.findById(askDto.getSeq())
                .orElseThrow(() -> new CustomException(ErrorCode.ASK_NOT_FOUND));
        // 요청 처리 여부 확인
        if(AskTypeEnum.CONFIRM.equals(ask.getType())){
            throw new CustomException(ErrorCode.ASK_ALREADY_CONFIRM);
        }
        // TODO(337): 처리자 세팅
        // ask.confirmAsk(askDto, user);

        ask.confirmAsk(askDto);

        return GlobalResponseDto.ok("요청 처리 성공");
    }

    @Transactional
    public GlobalResponseDto<Long> getAskCount() {
        return GlobalResponseDto.ok("요청 현황 건수", askRepository.countAskByType(AskTypeEnum.ASK));
    }
}
