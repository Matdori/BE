package com.example.beepoo.repository;

import com.example.beepoo.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByDepartmentName(String departmentName);

    Optional<Department> findDepartmentByDepartmentName(String departmentName);
}
