package com.example.beepoo.dto;

import com.example.beepoo.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponseDto{

    private String userName;

    private String userEmail;

    private String position;

    private String department;

    private String role;


    public UserResponseDto(User user){
        this.userName = user.getUserName();
        this.userEmail = user.getUserEmail();
        this.position = user.getPosition();
        this.department = user.getDepartmentName();
        this.role = user.getUserRole().toString();
    }
}