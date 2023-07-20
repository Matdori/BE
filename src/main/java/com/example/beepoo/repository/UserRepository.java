package com.example.beepoo.repository;

import com.example.beepoo.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserName(String userName);

    boolean existsByUserEmail(String userEmail);

    Optional<User> findUserByUserEmail(String userEmail);

    List<User> findUsersByDepartmentName(String departmentName);
}
