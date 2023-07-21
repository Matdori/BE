package com.example.beepoo.util;

import com.example.beepoo.entity.User;
import com.example.beepoo.enums.UserRoleEnum;
import com.example.beepoo.exception.CustomException;
import com.example.beepoo.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;

public class CustomUtil {
    public static boolean isAdmin(User user){
        return user.getUserRole().equals(UserRoleEnum.ADMIN);
    }

    public static User getUserFromReq(HttpServletRequest req){
        return (User)req.getAttribute("user");
    }

    public static void checkAuthorization(User user) {
        if (!isAdmin(user)){
            throw new CustomException(ErrorCode.USER_UNAUTHORIZED);
        }
    }

}
