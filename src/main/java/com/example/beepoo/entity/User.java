package com.example.beepoo.entity;

import com.example.beepoo.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class User extends TimeStamp{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userName;

    @Column
    private String userEmail;

    @Column
    private String userPassword;

    @Column
    private String position;

    @Column
    private String authority;

    @Column
    private String departmentName;

    @JoinColumn
    @ManyToOne
    private Department department;

    public User(UserRequestDto userRequestDto){
        this.userName = userRequestDto.getUserName();
        this.userEmail = userRequestDto.getUserEmail();
        this.userPassword = userRequestDto.getUserPassword();
        this.position = userRequestDto.getPosition();
        this.authority = userRequestDto.getAuthority();
        this.departmentName = userRequestDto.getDepartmentName();
    }
    public void updateUser(UserRequestDto userRequestDto){
        this.userName = userRequestDto.getUserName();
        this.userEmail = userRequestDto.getUserEmail();
        this.userPassword = userRequestDto.getUserPassword();
        this.position = userRequestDto.getPosition();
        this.authority = userRequestDto.getAuthority();
        this.departmentName = userRequestDto.getDepartmentName();
    }

}
