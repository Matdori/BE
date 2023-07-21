package com.example.beepoo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PasswordRequestDto {
    private Long userId;
    private String oldPassword;
    private String newPassword;
}
