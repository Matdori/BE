package com.example.beepoo.service;

import com.example.beepoo.dto.TestRequestDto;
import com.example.beepoo.dto.TestResponseDto;
import com.example.beepoo.entity.TestEntity;
import com.example.beepoo.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    @Transactional
    public ResponseEntity<TestResponseDto> testService(TestRequestDto testRequestDto) {
        String responseWord = testRequestDto.getTestWord();
        TestEntity testEntity = new TestEntity();
        testEntity.setWord(responseWord);
        testRepository.save(testEntity);
        TestEntity result = testRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException());
        String resultS = result.getWord();
        return ResponseEntity.ok(TestResponseDto.builder()
                .word(resultS)
                .build()
        );
    }
}
