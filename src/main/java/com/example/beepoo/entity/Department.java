package com.example.beepoo.entity;

import com.example.beepoo.dto.DepartmentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "department")
@NoArgsConstructor
@Getter @Setter
public class Department extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String departmentName;

    @Column(nullable = false)
    private Integer userCount;

    @OneToMany(mappedBy = "department")
    private List<User> userList = new ArrayList<>();

    public Department(DepartmentRequestDto departmentRequestDto) {
        this.departmentName = departmentRequestDto.getDepartmentName();
        this.userCount = 0;
    }

    public void update(DepartmentRequestDto departmentRequestDto) {
        this.departmentName = departmentRequestDto.getDepartmentName();
    }

}
