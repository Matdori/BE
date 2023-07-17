package com.example.beepoo.repository;

import com.example.beepoo.dto.AskRequestDto;
import com.example.beepoo.dto.AskResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AskCustomRepository {

    List<AskResponseDto> getAskList(AskRequestDto condition, Pageable pageable);

}
