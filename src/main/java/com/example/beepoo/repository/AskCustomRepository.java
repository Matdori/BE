package com.example.beepoo.repository;

import com.example.beepoo.dto.AskRequestDto;
import java.util.Map;
import org.springframework.data.domain.Pageable;

public interface AskCustomRepository {
    Map<String, Object> getAskList(AskRequestDto condition, Pageable pageable);
}
