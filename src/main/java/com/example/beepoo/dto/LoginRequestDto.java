package com.example.beepoo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequestDto {
    private String userEmail;
    private String userPassword;
}
