package com.example.beepoo.entity;

import com.example.beepoo.dto.DepartmentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "department")
@NoArgsConstructor
@Getter
public class Department extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DEPARTMENT_ID;

    @Column(nullable = false)
    private String departmentName;

    @Column(nullable = false)
    private Integer USER_COUNT;

    @OneToMany(mappedBy = "department")
    private List<User> userList = new ArrayList<>();

    public Department(DepartmentRequestDto departmentRequestDto) {
        this.departmentName = departmentRequestDto.getDepartmentName();
        this.USER_COUNT = 0;
    }

    public void update(DepartmentRequestDto departmentRequestDto) {
        this.departmentName = departmentRequestDto.getDepartmentName();
    }

}
