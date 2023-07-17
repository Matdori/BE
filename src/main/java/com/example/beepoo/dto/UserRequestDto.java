package com.example.beepoo.dto;

import lombok.Data;

@Data
public class UserRequestDto {

    private String userName;

    //ToDo[07] : Validation
    private String userEmail;

    private String userPassword;

    private String position;

    private String authority;

    private String departmentName;
}
