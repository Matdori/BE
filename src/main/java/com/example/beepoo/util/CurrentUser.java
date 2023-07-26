package com.example.beepoo.util;

import com.example.beepoo.entity.User;
import com.example.beepoo.enums.UserRoleEnum;
import com.example.beepoo.exception.CustomException;
import com.example.beepoo.exception.ErrorCode;
import com.example.beepoo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUser {


    private final UserRepository userRepository;

    public static User getUser(HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        return user;
    }

    public static Long getUserId(HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        return user.getId();
    }

    public static String getUserName(HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        return user.getUserName();
    }

    public static UserRoleEnum getUserRole(HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        return user.getUserRole();
    }

    public static boolean isAdmin(User user) {
        return user.getUserRole().equals(UserRoleEnum.ADMIN);
    }

    public static void checkAuthorization(User user) {
        if (!isAdmin(user)) {
            throw new CustomException(ErrorCode.USER_UNAUTHORIZED);
        }
    }
}
