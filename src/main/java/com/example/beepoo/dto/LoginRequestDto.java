package com.example.beepoo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequestDto {
    @Schema(description = "Email", example = "example.email.com")
    private String userEmail;
    @Schema(description = "비밀번호", example = "password123!")
    private String userPassword;
}
