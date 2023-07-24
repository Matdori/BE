package com.example.beepoo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDto {

    @NotBlank
    @Size(min = 1, max = 10, message = "이름이 비 상식적이다 임마")
    @Schema(description = "사원 이름", example = "정성우")
    private String userName;

    //ToDo[07] : Validation
//    @Email
    @Pattern(regexp = "^[a-zA-z0-9]+@[a-zA-Z0-9]+\\.[a-z]+$", message = "이메일 형식을 확인해 주세요.")
    @Schema(description = "email", example = "example@email.com")
    private String userEmail;

    @Schema(description = "비밀번호", example = "비밀번호123!")
    private String userPassword;

    @Schema(description = "직급", example = "대리")
    private String position;

    //ToDo[07] : Enum valid 추가해야겠지?
    @JsonProperty("isAdmin")
    @Schema(description = "관리자 여부", example = "1")
    private boolean isAdmin;

    @Schema(description = "부서 명", example = "많관부")
    private String departmentName;

}
