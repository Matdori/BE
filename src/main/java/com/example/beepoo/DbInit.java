package com.example.beepoo;

import com.example.beepoo.entity.User;
import com.example.beepoo.enums.UserRoleEnum;
import com.example.beepoo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbInit {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostConstruct
    private void init() {
        // 초기 admin 계정 추가
        if (!userRepository.existsByUserName("admin")) {
            String encodedPassword = passwordEncoder.encode("beepoo123");

            User user = new User();
            user.setUserName("admin");
            user.setUserEmail("admin");
            user.setUserPassword(encodedPassword);
            user.setPosition("admin");
            user.setUserRole(UserRoleEnum.ADMIN);
            user.setCreateUser("SYSTEM");

            userRepository.save(user);
        }
    }
}
