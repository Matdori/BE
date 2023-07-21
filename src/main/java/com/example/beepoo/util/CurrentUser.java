package com.example.beepoo.util;

import com.example.beepoo.entity.User;
import com.example.beepoo.enums.UserRoleEnum;
import com.example.beepoo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrentUser {

    @Autowired
    UserRepository userRepository;

    public User getUser(HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        return user;
    }

    public Long getUserId(HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        return user.getId();
    }

    public String getUserName(HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        return user.getUserName();
    }

    public UserRoleEnum getUserRole(HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        return user.getUserRole();
    }
}
