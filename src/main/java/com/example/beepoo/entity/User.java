package com.example.beepoo.entity;

import com.example.beepoo.dto.UserRequestDto;
import com.example.beepoo.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum userRole;

    @Column
    private String departmentName;

    @JoinColumn
    @ManyToOne
    private Department department;

    public User(UserRequestDto userRequestDto, String encodedPassword) {
        this.userName = userRequestDto.getUserName();
        this.userEmail = userRequestDto.getUserEmail();
        this.userPassword = encodedPassword;
        this.position = userRequestDto.getPosition();
        this.userRole = userRequestDto.isAdmin() ? UserRoleEnum.ADMIN : UserRoleEnum.USER;
        this.departmentName = userRequestDto.getDepartmentName();
    }

    public void updateUser(UserRequestDto userRequestDto) {
        this.userName = userRequestDto.getUserName();
        this.userEmail = userRequestDto.getUserEmail();
        this.position = userRequestDto.getPosition();
        this.userRole = userRequestDto.isAdmin() ? UserRoleEnum.ADMIN : UserRoleEnum.USER;
        this.departmentName = userRequestDto.getDepartmentName();
    }

}
