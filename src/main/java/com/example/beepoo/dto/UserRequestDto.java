package com.example.beepoo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDto {

    @NotBlank
    @Size(min = 1, max = 10, message = "이름이 비 상식적이다 임마")
    private String userName;

    //ToDo[07] : Validation
//    @Email
    @Pattern(regexp = "^[a-zA-z0-9]+@[a-zA-Z0-9]+\\.[a-z]+$", message = "이메일 형식을 확인해 주세요.")
    private String userEmail;

    private String userPassword;

    private String position;

    //ToDo[07] : Enum valid 추가해야겠지?
    @JsonProperty("isAdmin")
    private boolean isAdmin;

    private String departmentName;

}
