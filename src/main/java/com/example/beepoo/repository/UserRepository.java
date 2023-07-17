package com.example.beepoo.repository;

import com.example.beepoo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserEmail(String userEmail);

    Optional<User> findUserByUserEmail(String userEmail);

    List<User> findUsersByDepartmentName(String departmentName);
}
