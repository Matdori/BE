package com.example.beepoo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PasswordRequestDto {
    @Schema(description = "user ID", example = "1")
    private Long userId;
    @Schema(description = "기존 비밀번호", example = "password123!")
    private String oldPassword;
    @Schema(description = "신규 비밀번호", example = "password123@")
    private String newPassword;
}
